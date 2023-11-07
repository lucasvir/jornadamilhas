package com.jornadamilhas.api.dto.destiny;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record DestinyCreateDto(
        @NotBlank
        String name,

        @NotBlank
        String imgs,

        @NotBlank
        String price,

        @NotBlank
        @Size(min = 1, max = 160)
        String meta,

        @Size(max = 450)
        String description

) {

        public DestinyCreateDto(String name, String imgs, String meta) {
                this(name, imgs, imgs, meta, null);
        }
}
