package io.protopie.vendingmachine.beverage;

public class Beverage {

    private String name;
    private int price;
    private int stockQuantity;

    public Beverage(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String name() {
        return name;
    }

    public int price() {
        return price;
    }

    public void updatePrice(int price) {
        this.price = price;
    }

    public int stockQuantity() {
        return stockQuantity;
    }

    public void updateStockQuantity(int quantity) {
        this.stockQuantity += quantity;
    }

    public void decrementStock() {
        if (stockQuantity > 0) {
            stockQuantity--;
        } else {
            throw new IllegalStateException("Stock is already zero");
        }
    }

}
