package org.example.checkout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PricingRuleGeneratorTest {

    private PricingRuleGenerator pricingRuleGenerator;

    private PricingRule pricingRule;

    private String itemSku ="SKU";
    private String itemName ="name";
    private Double itemPrice = 10.00;

    private String bundleSku ="bundleSKU";
    private String bundleName ="bundle";
    private Double bundlePrice = 5.00;

    private Item item;
    private Item bundleItem;
    private Item otherItem;
    @BeforeEach
    void setUp() {
        pricingRuleGenerator = new PricingRuleGenerator();

        item = new Item(itemSku, itemName, itemPrice);
        bundleItem = new Item(bundleSku, bundleName, bundlePrice);
        otherItem = new Item("", "", 1.0);
    }

    @Test
    void givenAListWIthFewerThanTheMinimumNumberToTriggerTheRule_getXForYDeal_shouldReturnZero() {
        int triggerAmount = 2;
        int adjustedAmount = 1;

        pricingRule = pricingRuleGenerator.getXForYDeal(triggerAmount, adjustedAmount, item);

        assertEquals(0, pricingRule.getAdjustment(List.of(item)));
    }

    @Test
    void givenAListWithEnoughItemsToTriggerTheRuleOnce_getXForYDeal_shouldReturnTheAmountToReduceTheTotalBy() {
        int triggerAmount = 3;
        int adjustedAmount = 2;

        pricingRule = pricingRuleGenerator.getXForYDeal(triggerAmount, adjustedAmount, item);

        assertEquals(itemPrice, pricingRule.getAdjustment(List.of(item, item, item, item)));
    }

    @Test
    void givenAListWithEnoughItemsToTriggerTheRuleMultipleTimes_getXForYDeal_shouldReturnTheAmountToReduceTheTotalBy() {
        int triggerAmount = 3;
        int adjustedAmount = 1;

        pricingRule = pricingRuleGenerator.getXForYDeal(triggerAmount, adjustedAmount, item);

        assertEquals(itemPrice * 6,
                pricingRule.getAdjustment(List.of(item, item, item, item, item, item, item, item, item, item)));
    }

    @Test
    void givenAListWIthFewerThanTheMinimumNumberToTriggerTheRule_getBulkDiscount_shouldReturnZero() {
        int triggerAmount = 3;
        double discount = 3;

        pricingRule = pricingRuleGenerator.getBulkDiscount(triggerAmount, discount, item);

        assertEquals(0,
                pricingRule.getAdjustment(List.of(item)));
    }

    @Test
    void givenAListWithEnoughItemsToTriggerTheRule_getBulkDiscount_getBulkDiscount_shouldTheAmountToReduceTheTotalBy() {
        int triggerAmount = 3;
        double discount = 3;

        pricingRule = pricingRuleGenerator.getBulkDiscount(triggerAmount, discount, item);

        assertEquals(  5 * discount,
                pricingRule.getAdjustment(List.of(item, item, item, item, item)));
    }

    @Test
    void givenAListWithNeitherOfTheSpecifiedItems_getBundle_shouldReturnZero() {
        pricingRule = pricingRuleGenerator.getItemBundle(item, bundleItem);

        assertEquals( 0,
                pricingRule.getAdjustment(List.of(otherItem)));
    }

    @Test
    void givenAListWithEqualNumbersOfOfTheSpecifiedItems_getBundle_shouldReturnThePriceAdjustmentForAllTheBundledItems() {
        pricingRule = pricingRuleGenerator.getItemBundle(item, bundleItem);

        assertEquals( bundlePrice * 2,
                pricingRule.getAdjustment(List.of(otherItem, item, bundleItem, item, bundleItem)));
    }

    @Test
    void givenAListWithMoreBaseItemsThanBundledItems_getBundle_shouldReturnThePriceAdjustmentForAllTheBundledItems() {
        pricingRule = pricingRuleGenerator.getItemBundle(item, bundleItem);

        assertEquals( bundlePrice,
                pricingRule.getAdjustment(List.of(otherItem, item, item, bundleItem, item, item, item)));
    }

    @Test
    void givenAListWithFewerBaseItemsThanBundledItems_getBundle_shouldReturnThePriceAdjustmentForAsManyBundledItemsAsThereAreBaseItems() {
        pricingRule = pricingRuleGenerator.getItemBundle(item, bundleItem);

        assertEquals( bundlePrice * 3,
                pricingRule.getAdjustment(
                        List.of(item, item, item, bundleItem, bundleItem, bundleItem, bundleItem,bundleItem)));
    }
}