package io.protopie.vendingmachine.state;

import io.protopie.vendingmachine.VendingMachine;
import io.protopie.vendingmachine.beverage.Beverage;

public final class Dispensing implements VendingMachineState {

    @Override
    public void dispense(VendingMachine vendingMachine) {
        Beverage beverage = vendingMachine.selectedBeverage();
        beverage.decrementStock();
        System.out.println("Dispensing: " + beverage.name());
        System.out.println("Enjoy your " + beverage.name() + "!");
        vendingMachine.reset();
    }

    @Override
    public void cancel(VendingMachine vendingMachine) {
        System.out.println("Cancellation not allowed while dispensing.");
    }

    @Override
    public String name() {
        return "DispensingState";
    }

}
