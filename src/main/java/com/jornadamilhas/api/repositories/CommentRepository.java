package com.jornadamilhas.api.repositories;


import com.jornadamilhas.api.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
