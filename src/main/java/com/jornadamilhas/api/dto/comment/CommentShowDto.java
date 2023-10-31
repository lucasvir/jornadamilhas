package com.jornadamilhas.api.dto.comment;

import com.jornadamilhas.api.models.Comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record CommentShowDto(
        Long id,
        String text,
        LocalDateTime date,
        Long user_id
) {
    public CommentShowDto(Comment comment) {
        this(
                comment.getId(),
                comment.getText(),
                comment.getDate(),
                comment.getUser().getId()
        );
    }
}
