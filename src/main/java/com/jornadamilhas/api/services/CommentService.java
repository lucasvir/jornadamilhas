package com.jornadamilhas.api.services;

import com.jornadamilhas.api.dto.comment.CommentCreateDto;
import com.jornadamilhas.api.dto.comment.CommentShowDto;
import com.jornadamilhas.api.dto.comment.CommentUpdateDto;
import com.jornadamilhas.api.models.Comment;
import com.jornadamilhas.api.models.User;
import com.jornadamilhas.api.repositories.CommentRepository;
import com.jornadamilhas.api.repositories.UserRepository;
import com.jornadamilhas.api.services.exceptions.NotValidException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public CommentShowDto create(Long id, CommentCreateDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotValidException("Usuário não encontrado. Id -> " + id));

        Comment comment = new Comment(dto, user);
        commentRepository.save(comment);

        return new CommentShowDto(comment);
    }

    public List<CommentShowDto> index() {
        List<Comment> list = commentRepository.findAll();
        List<CommentShowDto> listDto = new ArrayList<>();


        list.forEach(c -> {
            listDto.add(new CommentShowDto(c));
        });

        return listDto;
    }

    public CommentShowDto show(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotValidException("Comentário não encontrado. Id -> " + id));

        return new CommentShowDto(comment);
    }

    public CommentShowDto update(Long id, CommentUpdateDto dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotValidException("Comentário não encontrado. Id -> " + id));

        comment.updateData(dto);
        commentRepository.save(comment);

        return new CommentShowDto(comment);
    }

    public void delete(Long id) {
        show(id); //cheking id exists

        commentRepository.deleteById(id);
    }

    public List<CommentShowDto> radomComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentShowDto> dtoList = new ArrayList<>();

        int maxCount = 3;
        int i = 0;
        var random = new Random();

        while (i < maxCount) {
            int randomNum = random.nextInt(comments.size() - 1) + 1;
            Comment comment = comments.get(randomNum);
            CommentShowDto dtoComment = new CommentShowDto(comment);

            var commentExists = dtoList.contains(dtoComment);

            if (!commentExists) {
                dtoList.add(dtoComment);
                i++;
            }
        }

        return dtoList;
    }
}
