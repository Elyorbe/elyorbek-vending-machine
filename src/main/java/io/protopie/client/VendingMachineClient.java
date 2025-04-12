package io.protopie.client;

import io.protopie.vendingmachine.VendingMachine;
import io.protopie.vendingmachine.beverage.BeverageInventory;
import io.protopie.vendingmachine.payment.CardPayment;


public class VendingMachineClient {

    public static void main(String[] args) {
        BeverageInventory repository = new BeverageInventory();
        VendingMachine machine = new VendingMachine(repository);

        System.out.println("=== Scenario 1: Cash Payment for Cola ===");
        machine.selectBeverage("cola");
        machine.insertCash(50_000);
        machine.insertCash(1000);
        machine.insertCash(1000);
        machine.dispense();
        System.out.println();

        System.out.println("=== Scenario 2: Card Payment for Water ===");
        machine.selectBeverage("water");
        CardPayment card = new CardPayment(1000);  // Sufficient balance
        machine.payWithCard(card);
        machine.dispense();
        System.out.println();

        System.out.println("=== Scenario 3: Failed Card Payment for Coffee ===");
        machine.selectBeverage("coffee");
        CardPayment insufficientCard = new CardPayment(500); // Insufficient funds
        machine.payWithCard(insufficientCard);
        System.out.println();

        System.out.println("=== Scenario 4: Cancel Transaction After Partial Cash Payment ===");
        machine.selectBeverage("cola");
        machine.insertCash(500);   // Partial cash insertion
        machine.cancel();
        System.out.println();

        System.out.println("=== Scenario 5: Cancel Transaction Before Payment Method is Chosen ===");
        machine.selectBeverage("water");
        machine.cancel();
        System.out.println();

        System.out.println("=== Scenario 6: Attempt to Cancel in Idle State ===");
        // Machine is idle because previous transaction was reset.
        machine.cancel();
        System.out.println();

        System.out.println("=== Scenario 7: Attempt to Cancel During Dispensing ===");
        machine.selectBeverage("coffee");
        machine.insertCash(1000);  // Sufficient, so state moves to Dispensing
        machine.cancel();          // Cancellation not allowed during dispensing.
        System.out.println();
    }

}

