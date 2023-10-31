package com.jornadamilhas.api.dto.user;

import com.jornadamilhas.api.models.User;

public record UserShowDto(
        Long id,
        String name,
        String email,
        String imgUrl
) {
    public UserShowDto(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getImgUrl()
        );
    }
}
