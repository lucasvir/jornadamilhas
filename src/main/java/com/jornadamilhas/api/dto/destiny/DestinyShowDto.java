package com.jornadamilhas.api.dto.destiny;

import com.jornadamilhas.api.models.Destiny;

import java.math.BigDecimal;
import java.util.List;

public record DestinyShowDto(
        Long id,
        String name,
        String imgs,
        String meta,
        String description
) {
    public DestinyShowDto(Destiny destiny) {
        this(
                destiny.getId(),
                destiny.getName(),
                stringImgToList(destiny.getImgs()),
                destiny.getMeta(),
                destiny.getDescription()
        );
    }

    private static String stringImgToList(List<String> imgs) {
        StringBuilder stringBuilder = new StringBuilder();
        imgs.forEach(stringBuilder::append);

        return stringBuilder.toString();
    }
}
