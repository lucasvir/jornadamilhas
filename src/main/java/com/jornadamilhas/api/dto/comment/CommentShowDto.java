package com.jornadamilhas.api.dto.comment;

import com.jornadamilhas.api.models.Comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record CommentShowDto(
        Long id,
        String text,
        String user_name,
        String user_imgUrl
) {
    public CommentShowDto(Comment comment) {
        this(
                comment.getId(),
                comment.getText(),
                comment.getUser().getName(),
                comment.getUser().getImgUrl()
        );
    }
}
