package org.example.checkout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutIntegrationTest {
    private final PricingRuleGenerator ruleGenerator = new PricingRuleGenerator();

    private Checkout checkout;

    private Item ipad;
    private Item macbookPro;
    private Item appleTv;
    private Item vgaCable;

    private PricingRule threeForTwoAppleTV;
    private PricingRule iPadBulkDiscount;
    private PricingRule macBookProVGABundle;

    @BeforeEach
    void setUp() {

        ipad = new Item("ipd", "Super iPad", 549.99);
        macbookPro = new Item("mbp", "MacBook Pro", 1399.99);
        appleTv = new Item("atv", "Apple TV", 109.50);
        vgaCable = new Item("vga", "VGA adapter", 30.00);

        threeForTwoAppleTV = ruleGenerator.getXForYDeal(3, 2, appleTv);
        iPadBulkDiscount = ruleGenerator.getBulkDiscount(4, 50.0, ipad);
        macBookProVGABundle = ruleGenerator.getItemBundle(macbookPro, vgaCable);

        checkout = new Checkout(List.of(threeForTwoAppleTV, iPadBulkDiscount, macBookProVGABundle));
    }

    @Test
    void exampleOne() {
        double expectedTotal = 249.00;
        List<Item> itemsScanned = List.of(appleTv, appleTv, appleTv, vgaCable);

        itemsScanned.forEach(this.checkout::scan);

        assertEquals(expectedTotal, checkout.total());
    }

    @Test
    void exampleTwo() {
        double expectedTotal = 2718.95;
        List<Item> itemsScanned = List.of(appleTv, ipad, ipad, appleTv, ipad, ipad, ipad);

        itemsScanned.forEach(this.checkout::scan);

        assertEquals(expectedTotal, checkout.total());
    }

    @Test
    void exampleThree() {
        double expectedTotal = 1949.98;
        List<Item> itemsScanned = List.of(macbookPro, vgaCable, ipad);

        itemsScanned.forEach(this.checkout::scan);

        assertEquals(expectedTotal, checkout.total());
    }
}
