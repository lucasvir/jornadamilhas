package com.jornadamilhas.api.dto.comment;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateDto(
        @NotBlank
        String text
) {
}
