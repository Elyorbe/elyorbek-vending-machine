package io.protopie.vendingmachine.state;

import io.protopie.vendingmachine.VendingMachine;
import io.protopie.vendingmachine.payment.CardPayment;

public final class AwaitingPaymentMethod implements VendingMachineState {

    @Override
    public void insertCash(VendingMachine vendingMachine, int amount) {
        System.out.println("Payment with cash initiated.");
        AwaitingCashPayment awaitingCashPayment = new AwaitingCashPayment();
        vendingMachine.changeState(awaitingCashPayment);
        awaitingCashPayment.insertCash(vendingMachine, amount);
    }

    @Override
    public void payWithCard(VendingMachine vendingMachine, CardPayment card) {
        System.out.println("Payment with card initiated.");
        AwaitingCardPayment awaitingCardPayment = new AwaitingCardPayment();
        vendingMachine.changeState(awaitingCardPayment);
        awaitingCardPayment.payWithCard(vendingMachine, card);
    }

    @Override
    public void cancel(VendingMachine vendingMachine) {
        System.out.println("Cancelling transaction from AwaitingPaymentMethod state.");
        vendingMachine.changeState(new Cancelled());
        vendingMachine.currentState().cancel(vendingMachine);
    }

    @Override
    public String name() {
        return "AwaitingPaymentMethodState";
    }

}
