package io.protopie.vendingmachine;

import io.protopie.vendingmachine.beverage.Beverage;
import io.protopie.vendingmachine.beverage.BeverageInventory;
import io.protopie.vendingmachine.payment.CardPayment;
import io.protopie.vendingmachine.state.Idle;
import io.protopie.vendingmachine.state.VendingMachineState;

public class VendingMachine {

    private VendingMachineState state;
    private Beverage selectedBeverage;
    private BeverageInventory beverageInventory;
    private int insertedCash;

    public VendingMachine(BeverageInventory beverageInventory) {
        this.beverageInventory = beverageInventory;
        state = new Idle();
        selectedBeverage = null;
    }

    public void selectBeverage(String beverage) {
        state.selectBeverage(this, beverage);
    }

    public void insertCash(int amount) {
        state.insertCash(this, amount);
    }

    public void payWithCard(CardPayment card) {
        state.payWithCard(this, card);
    }

    public void dispense() {
        state.dispense(this);
    }

    public void cancel() {
        state.cancel(this);
    }

    public int insertedCash() {
        return insertedCash;
    }

    public void addCash(int amount) {
        insertedCash += amount;
    }

    public Beverage selectedBeverage() {
        return selectedBeverage;
    }

    public void updateSelectedBeverage(String selectedBeverage) {
        this.selectedBeverage = beverageInventory.findByName(selectedBeverage)
                .orElseThrow(() -> new IllegalArgumentException("Selected beverage not available"));
    }

    public VendingMachineState currentState() {
        return state;
    }

    public void changeState(VendingMachineState state) {
        this.state = state;
    }

    public void reset() {
        System.out.println("Resetting to Idle state");
        state = new Idle();
        selectedBeverage = null;
        insertedCash = 0;
    }

}
