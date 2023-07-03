package com.inditex.pricesdemo.service;

import com.inditex.pricesdemo.domain.QPrice;
import com.inditex.pricesdemo.repository.PriceRepository;
import com.inditex.pricesdemo.rest.dto.PriceDto;
import com.inditex.pricesdemo.service.mapper.PriceMapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Transactional
@Service
public class PriceServiceImpl extends FilterOperation implements PriceService {

    private static final String PRIORITY = "priority";
    private static final boolean PARALLEL = false;

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    public PriceDto getByParams(LocalDateTime date, Long productId, Long brandId) {
        Predicate predicate = getPredicate(date, productId, brandId);
        Sort sort = Sort.by(Sort.Direction.DESC, PRIORITY);

        return StreamSupport.stream(priceRepository.findAll(predicate, sort).spliterator(), PARALLEL)
                .findFirst()
                .map(priceMapper::toDto)
                .orElseThrow(() -> new RuntimeException());
    }

    private BooleanBuilder getPredicate(LocalDateTime date, Long productId, Long brandId) {
        BooleanBuilder builder = new BooleanBuilder();

        QPrice price = QPrice.price;

        super.and(builder, date, price.startDate::before);
        super.and(builder, date, price.endDate::after);
        super.and(builder, productId, price.productId::eq);
        super.and(builder, brandId, price.brandId::eq);
        return builder;
    }
}
