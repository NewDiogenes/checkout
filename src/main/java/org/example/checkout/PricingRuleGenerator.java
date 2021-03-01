package org.example.checkout;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class PricingRuleGenerator {

    public PricingRule getXForYDeal(Integer triggerAmount, Integer adjustedAmount, Item item) {
        return new PricingRule(getMinimumNumberPredicate(triggerAmount, item),
                getXForYPriceAdjustment(triggerAmount, adjustedAmount, item));
    }

    public PricingRule getBulkDiscount(Integer triggerAmount, Double discountAmountDollars, Item item) {
        return new PricingRule(getMinimumNumberPredicate(triggerAmount, item),
                getBulkDiscountAdjustment(discountAmountDollars, item));
    }

    public PricingRule getItemBundle(Item baseItem, Item bundledItem) {
        return new PricingRule(getBundlePredicate(baseItem, bundledItem),
                getBundlePriceAdjustment(baseItem, bundledItem));
    }

    private Predicate<List<Item>> getMinimumNumberPredicate(Integer triggerAmount, Item item) {
        return list -> getItemCount(item, list) >= triggerAmount;
    }

    private Predicate<List<Item>> getBundlePredicate(Item baseItem, Item bundledItem) {
        return list -> list.contains(baseItem) && list.contains(bundledItem);
    }

    private Function<List<Item>, Double> getBundlePriceAdjustment(Item baseItem, Item bundledItem) {
        return list -> Math.min(getItemCount(baseItem, list), getItemCount(bundledItem, list))
                * bundledItem.getPrice();
    }

    private Function<List<Item>, Double> getXForYPriceAdjustment(Integer triggerAmount,
                                                                 Integer adjustedAmount,
                                                                 Item item) {
        return list -> getItemCount(item, list)
                / triggerAmount
                * item.getPrice()
                * (triggerAmount - adjustedAmount);
    }

    private Function<List<Item>, Double> getBulkDiscountAdjustment(Double discountAmountDollars,
                                                                 Item item) {
        return list -> getItemCount(item, list)
                * discountAmountDollars;
    }

    private long getItemCount(Item item, List<Item> list) {
        return list.stream()
                .filter(item::equals)
                .count();
    }
}
