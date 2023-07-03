package com.inditex.pricesdemo.service;

import com.inditex.pricesdemo.rest.dto.PriceDto;

import java.time.LocalDateTime;

public interface PriceService {

    PriceDto getByParams(LocalDateTime date, Long productId, Long brandId);
}
