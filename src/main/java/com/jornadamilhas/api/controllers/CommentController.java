package com.jornadamilhas.api.controllers;

import com.jornadamilhas.api.dto.comment.CommentCreateDto;
import com.jornadamilhas.api.dto.comment.CommentShowDto;
import com.jornadamilhas.api.dto.comment.CommentUpdateDto;
import com.jornadamilhas.api.services.CommentService;
import com.jornadamilhas.api.services.exceptions.NotValidException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @PostMapping("{id}")
    @Transactional
    public ResponseEntity<CommentShowDto> create(@PathVariable Long id, @RequestBody @Valid CommentCreateDto dto) {
        CommentShowDto comment = service.create(id, dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(comment.id()).toUri();
        return ResponseEntity.created(uri).body(comment);
    }

    @GetMapping
    public ResponseEntity<List<CommentShowDto>> index() {
        List<CommentShowDto> list = service.index();
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<CommentShowDto> show(@PathVariable Long id) {
        CommentShowDto comment = service.show(id);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<CommentShowDto> update(@PathVariable Long id, @RequestBody @Valid CommentUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
            service.delete(id);
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/depoimentos-home")
    public ResponseEntity<List<CommentShowDto>> randomComments() {
        return ResponseEntity.ok(service.radomComments());
    }
}
