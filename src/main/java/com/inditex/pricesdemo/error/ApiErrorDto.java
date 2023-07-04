package com.inditex.pricesdemo.error;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class ApiErrorDto {

    Integer status;
    String message;
}
