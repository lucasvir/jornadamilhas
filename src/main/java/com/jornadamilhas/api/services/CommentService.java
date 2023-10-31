package com.jornadamilhas.api.services;

import com.jornadamilhas.api.dto.comment.CommentCreateDto;
import com.jornadamilhas.api.dto.comment.CommentShowDto;
import com.jornadamilhas.api.models.Comment;
import com.jornadamilhas.api.models.User;
import com.jornadamilhas.api.repositories.CommentRepository;
import com.jornadamilhas.api.repositories.UserRepository;
import com.jornadamilhas.api.services.exceptions.NotValidException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public CommentShowDto create(Long id, CommentCreateDto dto) {
        try {
            User user = userRepository.getReferenceById(id);
            Comment comment = new Comment(dto, user);
            commentRepository.save(comment);

            return new CommentShowDto(comment);
        } catch (EntityNotFoundException e) {
            throw new NotValidException("Usuário não encontrado. Id -> " + id);
        }
    }

    public List<CommentShowDto> index() {
        List<Comment> list = commentRepository.findAll();
        List<CommentShowDto> listDto = new ArrayList<>();


        list.forEach(c -> {
            listDto.add(new CommentShowDto(c));
        });

        return listDto;
    }
}
