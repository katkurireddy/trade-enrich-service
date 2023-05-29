package com.enrich.org.tradeenrichservice.model;

import java.util.Objects;

public record EnrichModel(Integer productId, String productName) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrichModel that = (EnrichModel) o;
        return Objects.equals(productId, that.productId) && Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName);
    }

    @Override
    public String toString() {
        return "EnrichModel{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                '}';
    }
}
