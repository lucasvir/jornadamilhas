package com.jornadamilhas.api.controllers;

import com.jornadamilhas.api.dto.user.UserCreateDto;
import com.jornadamilhas.api.dto.user.UserShowDto;
import com.jornadamilhas.api.models.User;
import com.jornadamilhas.api.services.UserService;
import com.jornadamilhas.api.services.exceptions.NotValidException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    @Transactional
    public ResponseEntity<UserShowDto> create(@RequestBody @Valid UserCreateDto dto) {

        try {
            User user = service.create(dto);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(user.getId()).toUri();

            return ResponseEntity.created(uri).body(new UserShowDto(user));
        } catch (NotValidException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> index() {
        List<User> users = service.index();

        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserShowDto> show(@PathVariable Long id) {
        try {
            User user = service.show(id);
            return ResponseEntity.ok(new UserShowDto(user));
        } catch (EntityNotFoundException e) {
            System.out.println("Usuário não encontrado. Id -> " + id);
            return ResponseEntity.badRequest().build();
        }
    }

}
