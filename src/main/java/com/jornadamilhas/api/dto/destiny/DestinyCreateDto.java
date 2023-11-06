package com.jornadamilhas.api.dto.destiny;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record DestinyCreateDto(
        @NotBlank
        String name,

        @NotBlank
        String imgUrl,

        @NotBlank
        String price
) {
}
