package com.jornadamilhas.api.controllers;

import com.jornadamilhas.api.dto.comment.CommentCreateDto;
import com.jornadamilhas.api.dto.comment.CommentShowDto;
import com.jornadamilhas.api.dto.comment.CommentUpdateDto;
import com.jornadamilhas.api.services.CommentService;
import com.jornadamilhas.api.services.exceptions.NotValidException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("depoimentos")
public class CommentController {

    @Autowired
    private CommentService service;


    @PostMapping("{id}/create")
    @Transactional
    public ResponseEntity<CommentShowDto> create(@PathVariable Long id, @RequestBody @Valid CommentCreateDto dto) {
        try {
            CommentShowDto comment = service.create(id, dto);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(comment.id()).toUri();
            return ResponseEntity.created(uri).body(comment);
        } catch (NotValidException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CommentShowDto>> index() {
        List<CommentShowDto> list = service.index();
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<CommentShowDto> show(@PathVariable Long id) {
        try {
            CommentShowDto comment = service.show(id);
            return ResponseEntity.ok(comment);
        } catch (NotValidException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{id}/update")
    @Transactional
    public ResponseEntity<CommentShowDto> update(@PathVariable Long id, @RequestBody @Valid CommentUpdateDto dto) {
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (NotValidException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NotValidException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/depoimentos-home")
    public ResponseEntity<List<CommentShowDto>> randomComments() {
        return ResponseEntity.ok(service.radomComments());
    }
}
