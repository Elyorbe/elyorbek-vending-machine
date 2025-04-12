package io.protopie.vendingmachine;

import io.protopie.vendingmachine.beverage.BeverageInventory;
import io.protopie.vendingmachine.payment.CardPayment;
import io.protopie.vendingmachine.state.AwaitingCashPayment;
import io.protopie.vendingmachine.state.AwaitingPaymentMethod;
import io.protopie.vendingmachine.state.Idle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineTests {

    private BeverageInventory inventory;
    private VendingMachine machine;

    @BeforeEach
    public void setup() {
        // Initialize the beverage inventory with default beverages.
        inventory = new BeverageInventory();
        // Create a new vending machine instance with the given inventory.
        machine = new VendingMachine(inventory);
    }

    @Test
    public void testSelectBeverageValid() {
        machine.selectBeverage("cola");
        // Ensure a beverage is selected
        assertNotNull(machine.selectedBeverage(), "Beverage should be selected");
        // Check if the selected beverage is "cola" (case-insensitive comparison)
        assertEquals("cola", machine.selectedBeverage().name().toLowerCase());
        // The state should now be AwaitingPaymentMethod
        assertInstanceOf(AwaitingPaymentMethod.class, machine.currentState(), "State should be AwaitingPaymentMethod after selection");
    }

    @Test
    public void testSelectBeverageInvalid() {
        // Request a beverage not in the inventory. Expect an IllegalArgumentException.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            machine.selectBeverage("nonexistent");
        });
        String expectedMessage = "Selected beverage not available";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void testValidCashPayment() {
        machine.selectBeverage("cola");
        // The user inserts 1000 KRW first; valid denominations (e.g., 1000 is accepted).
        machine.insertCash(1000);
        // Check inserted cash is updated correctly.
        assertEquals(1000, machine.insertedCash(), "Total inserted cash should be 1000 KRW");
        // If 1000 is less than the price of cola (1100), state should now be AwaitingCashPayment.
        assertInstanceOf(AwaitingCashPayment.class, machine.currentState(), "State should be AwaitingCashPayment");

        // Insert additional cash to reach or exceed the price.
        machine.insertCash(100);
        // Now, total cash (1100) meets the price.
        // Calling dispense should dispense the beverage and reset the machine.
        machine.dispense();

        // Verify that after dispensing the machine resets to Idle.
        assertInstanceOf(Idle.class, machine.currentState(), "Machine should reset to Idle after dispensing");
        // Inserted cash must be cleared.
        assertEquals(0, machine.insertedCash(), "Inserted cash should be reset to 0 after dispensing");
    }

    @Test
    public void testInvalidCashDenomination() {
        machine.selectBeverage("cola");
        // Insert a coin that is not accepted (e.g., 345 KRW is invalid).
        machine.insertCash(345);
        // The invalid coin should not be added to total inserted cash.
        assertEquals(0, machine.insertedCash(), "Inserted cash should remain 0 for invalid coin");
        // State should remain in AwaitingCashPayment to wait for a valid insertion.
        assertInstanceOf(AwaitingCashPayment.class, machine.currentState(), "State should remain in AwaitingCashPayment");
    }

    @Test
    public void testCancelTransaction() {
        machine.selectBeverage("cola");
        // Insert partial valid cash.
        machine.insertCash(500);
        // Cancel the transaction.
        machine.cancel();
        // After cancellation, the vending machine should be reset to Idle.
        assertInstanceOf(Idle.class, machine.currentState(), "State should be Idle after cancellation");
        // No beverage should remain selected.
        assertNull(machine.selectedBeverage(), "Selected beverage should be null after cancellation");
        // Inserted cash should be reset.
        assertEquals(0, machine.insertedCash(), "Inserted cash should be reset to 0 after cancellation");
    }

    @Test
    public void testCardPaymentSuccess() {
        machine.selectBeverage("water");
        // Create a CardPayment instance with sufficient balance.
        CardPayment card = new CardPayment(1000);
        machine.payWithCard(card);
        // Dispense should successfully dispense the beverage.
        machine.dispense();
        // The machine should reset to Idle.
        assertInstanceOf(Idle.class, machine.currentState(), "Machine should reset to Idle after dispensing");
    }

    @Test
    public void testCardPaymentFailure() {
        machine.selectBeverage("coffee");
        // Create a CardPayment instance with insufficient funds.
        CardPayment insufficientCard = new CardPayment(500);
        machine.payWithCard(insufficientCard);
        // Expect that payment failure causes the machine to reset to Idle.
        assertInstanceOf(Idle.class, machine.currentState(), "Machine should reset to Idle if card payment fails");
        // The selected beverage should be cleared after reset.
        assertNull(machine.selectedBeverage(), "Selected beverage should be null after card payment failure");
    }

}