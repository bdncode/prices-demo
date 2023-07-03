package com.inditex.pricesdemo.service;

import com.inditex.pricesdemo.domain.Price;
import com.inditex.pricesdemo.fixture.PriceFixtures;
import com.inditex.pricesdemo.repository.PriceRepository;
import com.inditex.pricesdemo.rest.dto.PriceDto;
import com.inditex.pricesdemo.service.mapper.PriceMapper;
import com.inditex.pricesdemo.service.mapper.PriceMapperImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    PriceRepository priceRepository;
    @Spy
    PriceMapper priceMapper = new PriceMapperImpl();
    @InjectMocks
    PriceService underTest = new PriceServiceImpl(priceRepository, priceMapper);

    @Test
    void getAll() {
        Price price = PriceFixtures.getPrice();

        PriceDto result = underTest.getByParams(null, null, null);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getProductId()).isEqualTo(price.getProductId());
        Assertions.assertThat(result.getBrandId()).isEqualTo(price.getBrandId());
        Assertions.assertThat(result.getPriceList()).isEqualTo(price.getPriceList());
        Assertions.assertThat(result.getStartDate()).isEqualTo(price.getStartDate());
        Assertions.assertThat(result.getEndDate()).isEqualTo(price.getEndDate());
        Assertions.assertThat(result.getFinalPrice()).isEqualTo(price.getFinalPrice());
    }

    @Test
    void getAllThrowsException() {
        Assertions.assertThatThrownBy(() -> underTest.getByParams(null, null, null))
                .isInstanceOf(RuntimeException.class);
    }
}