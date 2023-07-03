package com.inditex.pricesdemo.repository;

import com.inditex.pricesdemo.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PriceRepository extends JpaRepository<Price, Long>, QuerydslPredicateExecutor<Price> {
}
