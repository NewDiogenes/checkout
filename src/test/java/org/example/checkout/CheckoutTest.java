package org.example.checkout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class CheckoutTest {

    private Checkout checkout;

    @Mock
    private PricingRule alwaysFails;
    @Mock
    private PricingRule alwaysReduceByOne;

    private Item item;
    private String itemSku ="SKU";
    private String itemName ="name";
    private Double itemPrice = 10.00;

    private String otherSku ="otherSKU";
    private String otherName ="other";
    private Double otherPrice = 5.00;
    private Item otherItem;

    private double discountAmount = 3.5;

    @BeforeEach
    void setUp() {
        initMocks(this);

        item = new Item(itemSku, itemName, itemPrice);
        otherItem = new Item(otherSku, otherName, otherPrice);

        when(alwaysFails.getAdjustment(any())).thenReturn(0.0);
        when(alwaysReduceByOne.getAdjustment(any())).thenReturn(discountAmount);
    }

    @Test
    void givenNoDiscountsHaveBeenApplied_total_shouldReturnTheSumOfTheTotalPriceOfEachItem() {
        checkout = new Checkout(List.of(alwaysFails));

        checkout.scan(item);
        checkout.scan(otherItem);

        assertEquals(itemPrice + otherPrice, checkout.total());
    }

    @Test
    void givenDiscountsHaveBeenApplied_total_shouldReturnTheSumOfTheTotalPriceOfEachItemMinusTheDiscountValue() {
        checkout = new Checkout(List.of(alwaysFails, alwaysReduceByOne));

        checkout.scan(item);
        checkout.scan(otherItem);

        assertEquals(itemPrice + otherPrice - discountAmount, checkout.total());
    }
}