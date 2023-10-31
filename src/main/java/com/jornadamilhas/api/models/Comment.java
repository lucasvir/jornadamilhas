package com.jornadamilhas.api.models;

import com.jornadamilhas.api.dto.comment.CommentCreateDto;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "comments")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Comment() {

    }

    public Comment(Long id, String text, LocalDateTime date, User user) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.user = user;
    }

    public Comment(CommentCreateDto dto, User user) {

        this.text = dto.text();
        this.date = LocalDateTime.now();
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", user=" + user +
                '}';
    }
}
