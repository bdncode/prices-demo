package com.inditex.pricesdemo.service;

import com.inditex.pricesdemo.domain.Price;
import com.inditex.pricesdemo.error.exception.ResourceNotFoundException;
import com.inditex.pricesdemo.fixture.PriceFixtures;
import com.inditex.pricesdemo.repository.PriceRepository;
import com.inditex.pricesdemo.rest.dto.PriceDto;
import com.inditex.pricesdemo.service.mapper.PriceMapper;
import com.inditex.pricesdemo.service.mapper.PriceMapperImpl;
import com.querydsl.core.BooleanBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    PriceRepository priceRepository;
    @Spy
    PriceMapper priceMapper = new PriceMapperImpl();
    @InjectMocks
    PriceServiceImpl underTest;

    @Test
    @DisplayName("Busca un precio por sus parámetros correctamente")
    void getAll() {
        Price price = PriceFixtures.getPrice();
        Mockito.when(priceRepository.findAll(ArgumentMatchers.any(BooleanBuilder.class), ArgumentMatchers.any(Sort.class)))
                .thenReturn(List.of(price));

        PriceDto result = underTest.getByParams(price.getStartDate(), 35455L, 1L);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getProductId()).isEqualTo(price.getProductId());
        Assertions.assertThat(result.getBrandId()).isEqualTo(price.getBrandId());
        Assertions.assertThat(result.getPriceList()).isEqualTo(price.getPriceList());
        Assertions.assertThat(result.getStartDate()).isEqualTo(price.getStartDate());
        Assertions.assertThat(result.getEndDate()).isEqualTo(price.getEndDate());
        Assertions.assertThat(result.getFinalPrice()).isEqualTo(price.getFinalPrice());
    }

    @Test
    @DisplayName("Lanza excepción si se busca precio que no existe")
    void getAllThrowsException() {
        Mockito.when(priceRepository.findAll(ArgumentMatchers.any(BooleanBuilder.class), ArgumentMatchers.any(Sort.class)))
                .thenReturn(Collections.emptyList());

        Assertions.assertThatThrownBy(() -> underTest.getByParams(null, null, null))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No se ha podido encontrar un precio con parámetros de búsqueda introducidos");
    }
}
