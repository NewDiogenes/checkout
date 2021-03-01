package org.example.checkout;

import java.util.ArrayList;
import java.util.List;

public class Checkout {

    private final List<PricingRule> pricingRules;

    private List<Item> itemList;
    private Double basePrice;
    private Double adjustedPrice;

    public Checkout(List<PricingRule> pricingRules) {
        this.pricingRules = pricingRules;
        this.basePrice = 0.0;
        this.adjustedPrice = 0.0;
        this.itemList = new ArrayList<>();
    }

    public Double scan(Item newItem) {
        this.basePrice += newItem.getPrice();
        itemList.add(newItem);

        this.adjustedPrice = this.basePrice - getPriceAdjustments();

        return 0.0;
    }

    public Double total() {
        return this.adjustedPrice;
    }

    private Double getPriceAdjustments() {
        return pricingRules.stream()
                .mapToDouble(rule -> rule.getAdjustment(this.itemList))
                .sum();
    }
}
