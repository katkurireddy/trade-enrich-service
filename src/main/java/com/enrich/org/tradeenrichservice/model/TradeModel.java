package com.enrich.org.tradeenrichservice.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public record TradeModel(LocalDate date, Integer productId, String currency, BigDecimal price) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeModel that = (TradeModel) o;
        return Objects.equals(date, that.date) && Objects.equals(productId, that.productId) && Objects.equals(currency, that.currency) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, productId, currency, price);
    }

    @Override
    public String toString() {
        return "TradeModel{" +
                "date=" + date +
                ", productId=" + productId +
                ", currency='" + currency + '\'' +
                ", price=" + price +
                '}';
    }
}
