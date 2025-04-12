package io.protopie.vendingmachine.payment;

public final class CardPayment {

    private int cardBalance;

    public CardPayment(int cardBalance) {
        this.cardBalance = cardBalance;
    }

    public boolean pay(int amount) throws PaymentException {
        if (cardBalance < amount) {
            throw new PaymentException("Insufficient card balance. Required: " + amount
                    + " KRW, available: " + cardBalance + " KRW.");
        }
        cardBalance -= amount;
        System.out.println("[CardPayment] Charged: " + amount + " KRW. Remaining balance: " + cardBalance + " KRW.");
        return true;
    }

}
