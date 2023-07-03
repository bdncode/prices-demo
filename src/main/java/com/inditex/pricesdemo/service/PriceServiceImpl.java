package com.inditex.pricesdemo.service;

import com.inditex.pricesdemo.repository.PriceRepository;
import com.inditex.pricesdemo.rest.dto.PriceDto;
import com.inditex.pricesdemo.service.mapper.PriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    @Override
    public PriceDto getByParams(LocalDateTime date, Long productId, Long brandId) {
        return null;
    }
}
