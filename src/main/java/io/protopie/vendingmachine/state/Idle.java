package io.protopie.vendingmachine.state;

import io.protopie.vendingmachine.VendingMachine;
import io.protopie.vendingmachine.beverage.Beverage;

public final class Idle implements VendingMachineState {

    @Override
    public void selectBeverage(VendingMachine vendingMachine, String beverage) {
        vendingMachine.updateSelectedBeverage(beverage);
        Beverage selectedBeverage = vendingMachine.selectedBeverage();
        if (selectedBeverage.stockQuantity() < 1) {
            System.out.println(selectedBeverage + " is not in stock. Choose another beverage.");
            return;
        }
        System.out.println("Selected beverage: " + selectedBeverage.name() + " | Price: " + selectedBeverage.price() + " KRW");
        vendingMachine.changeState(new AwaitingPaymentMethod());
    }

    @Override
    public void cancel(VendingMachine vendingMachine) {
        System.out.println("No transaction in progress to cancel.");
    }

    @Override
    public String name() {
        return "IdleState";
    }

}
