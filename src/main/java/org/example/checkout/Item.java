package org.example.checkout;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Item {

    private String sku;
    private String name;
    private Double price;

    public Item(String sku, String name, Double price) {
        this.sku = sku;
        this.name = name;
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return new EqualsBuilder().append(sku, item.sku).append(name, item.name).append(price, item.price).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(sku).append(name).append(price).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sku", sku)
                .append("name", name)
                .append("price", price)
                .toString();
    }
}
