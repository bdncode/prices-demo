package com.inditex.pricesdemo.rest;

import com.inditex.pricesdemo.rest.dto.PriceDto;
import com.inditex.pricesdemo.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/prices")
@RestController
public class PriceController {

    private final PriceService priceService;

    @GetMapping
    public ResponseEntity<PriceDto> getByParams(@RequestParam(required = false) LocalDateTime date,
                                                @RequestParam(required = false) Long productId,
                                                @RequestParam(required = false) Long brandId) {
        return ResponseEntity.ok(priceService.getByParams(date, productId, brandId));
    }
}
