package io.protopie.vendingmachine.beverage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BeverageInventory {

    private final Map<String, Beverage> beverages = new HashMap<>();

    public BeverageInventory() {
        beverages.put("cola", new Beverage("cola", 1100, 10));
        beverages.put("water", new Beverage("water", 600, 15));
        beverages.put("coffee", new Beverage("coffee", 700, 8));
    }

    public Optional<Beverage> findByName(String name) {
        return Optional.ofNullable(beverages.get(name.toLowerCase()));
    }

    public Map<String, Beverage> findAll() {
        return Collections.unmodifiableMap(beverages);
    }

    public void updateStock(String name, int quantity) {
        Beverage beverage = beverages.get(name.toLowerCase());
        if (beverage == null) {
            throw new IllegalArgumentException(name + " doesn't exist");
        }
        beverage.updateStockQuantity(quantity);
    }

    public void updatePrice(String name, int newPrice) {
        Beverage beverage = beverages.get(name.toLowerCase());
        if (beverage == null) {
            throw new IllegalArgumentException(name + " doesn't exist");
        }
        beverage.updatePrice(newPrice);
    }

}
