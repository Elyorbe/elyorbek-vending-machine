package io.protopie.vendingmachine.state;

import io.protopie.vendingmachine.VendingMachine;
import io.protopie.vendingmachine.payment.CardPayment;
import io.protopie.vendingmachine.payment.PaymentException;

public final class AwaitingCardPayment implements VendingMachineState {

    @Override
    public void payWithCard(VendingMachine vendingMachine, CardPayment card) {
        int price = vendingMachine.selectedBeverage().price();
        try {
            if (card.pay(price)) {
                vendingMachine.changeState(new Dispensing());
            }
        } catch (PaymentException e) {
            System.out.println("Card payment failed: " + e.getMessage());
            vendingMachine.reset();
        }
    }

    @Override
    public void cancel(VendingMachine vendingMachine) {
        System.out.println("Cancelling transaction from AwaitingCardPayment state.");
        vendingMachine.changeState(new Cancelled());
        vendingMachine.currentState().cancel(vendingMachine);
    }

    @Override
    public String name() {
        return "AwaitingCardPaymentState";
    }

}
