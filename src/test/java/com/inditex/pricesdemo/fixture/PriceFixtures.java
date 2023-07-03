package com.inditex.pricesdemo.fixture;

import com.inditex.pricesdemo.domain.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceFixtures {

    public static Price getPrice() {
        Price price = new Price();
        price.setBrandId(1L);
        price.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0));
        price.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        price.setPriceList(1L);
        price.setProductId(35455L);
        price.setFinalPrice(new BigDecimal("35.50"));

        return price;
    }
}
