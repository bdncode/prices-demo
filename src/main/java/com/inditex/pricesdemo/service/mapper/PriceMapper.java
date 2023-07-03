package com.inditex.pricesdemo.service.mapper;

import com.inditex.pricesdemo.domain.Price;
import com.inditex.pricesdemo.rest.dto.PriceDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PriceMapper {

    PriceDto toDto(Price price);
}
