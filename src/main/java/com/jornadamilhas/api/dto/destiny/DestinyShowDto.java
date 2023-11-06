package com.jornadamilhas.api.dto.destiny;

import com.jornadamilhas.api.models.Destiny;

import java.math.BigDecimal;

public record DestinyShowDto(
        Long id,
        String name,
        String imgUrl,
        String price
) {
    public DestinyShowDto(Destiny destiny) {
        this(
                destiny.getId(),
                destiny.getName(),
                destiny.getImgUrl(),
                destiny.getPrice().toString()
        );
    }
}
