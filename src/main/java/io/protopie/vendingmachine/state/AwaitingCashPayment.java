package io.protopie.vendingmachine.state;

import io.protopie.vendingmachine.VendingMachine;
import io.protopie.vendingmachine.beverage.Beverage;

import java.util.Set;

public final class AwaitingCashPayment implements VendingMachineState {

    private static final Set<Integer> ACCEPTED_DENOMINATIONS = Set.of(100, 500, 1_000, 5_000, 10_000);

    @Override
    public void insertCash(VendingMachine vendingMachine, int amount) {
        if (!ACCEPTED_DENOMINATIONS.contains(amount)) {
            System.out.println("Error: Denomination " + amount + " KRW is not accepted. Cash returned.");
            return;
        }

        System.out.println("Cash inserted: " + amount + " KRW");
        vendingMachine.addCash(amount);

        Beverage selectedBeverage = vendingMachine.selectedBeverage();
        int insertedCash = vendingMachine.insertedCash();

        if (insertedCash >= selectedBeverage.price()) {
            int change = insertedCash - selectedBeverage.price();
            if (change > 0) {
                System.out.println("Returning change: " + change + " KRW");
            }
            vendingMachine.changeState(new Dispensing());
        } else {
            System.out.println("Not enough money. Please insert more.");
        }
    }

    @Override
    public void cancel(VendingMachine vendingMachine) {
        System.out.println("Cancelling transaction from AwaitingCashPayment state.");
        vendingMachine.changeState(new Cancelled());
        vendingMachine.currentState().cancel(vendingMachine);
    }

    @Override
    public String name() {
        return "AwaitingCashPaymentState";
    }

}
