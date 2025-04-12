package io.protopie.vendingmachine.state;


import io.protopie.vendingmachine.VendingMachine;
import io.protopie.vendingmachine.payment.CardPayment;

public sealed interface VendingMachineState
        permits AwaitingCardPayment, AwaitingCashPayment, AwaitingPaymentMethod, Cancelled, Dispensing, Idle {

    default void selectBeverage(VendingMachine vendingMachine, String beverage) {
        System.out.println("Can not select beverage in " + vendingMachine.currentState().name());
    }

    default void insertCash(VendingMachine vendingMachine, int amount) {
        System.out.println("Can not insert cash in " + vendingMachine.currentState().name());
    }

    default void payWithCard(VendingMachine vendingMachine, CardPayment card) {
        System.out.println("Can not pay with card in " + vendingMachine.currentState().name());
    }

    default void dispense(VendingMachine vendingMachine) {
        System.out.println("Can not dispense in " + vendingMachine.currentState().name());
    }

    default void cancel(VendingMachine vendingMachine) {
        System.out.println("Can not cancel in " + vendingMachine.currentState().name());
    }

    String name();

}
