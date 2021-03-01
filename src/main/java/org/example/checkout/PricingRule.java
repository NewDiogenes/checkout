package org.example.checkout;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class PricingRule {
    private final Predicate<List<Item>> rulePredicate;
    private final Function<List<Item>, Double> priceAdjustment;

    public PricingRule(Predicate<List<Item>> rulePredicate, Function<List<Item>, Double> priceAdjustment) {
        this.rulePredicate = rulePredicate;
        this.priceAdjustment = priceAdjustment;
    }

    public Double getAdjustment(List<Item> itemList) {
        return this.rulePredicate.test(itemList) ? this.priceAdjustment.apply(itemList) : 0;
    }
}
