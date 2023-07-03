package com.inditex.pricesdemo.repository;

import com.inditex.pricesdemo.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
