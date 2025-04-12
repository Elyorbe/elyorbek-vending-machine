package io.protopie.vendingmachine.state;

import io.protopie.vendingmachine.VendingMachine;

public final class Cancelled implements VendingMachineState {

    @Override
    public void cancel(VendingMachine vendingMachine) {
        int refundedCash = vendingMachine.insertedCash();
        if (refundedCash > 0) {
            System.out.println("Refunding cash: " + refundedCash + " KRW");
        }
        System.out.println("Transaction cancelled. Resetting machine.");
        vendingMachine.reset();
    }

    @Override
    public String name() {
        return "CancelledState";
    }

}
