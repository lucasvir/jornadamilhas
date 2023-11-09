package com.jornadamilhas.api.controllers;

import com.jornadamilhas.api.dto.user.UserCreateDto;
import com.jornadamilhas.api.dto.user.UserShowDto;
import com.jornadamilhas.api.models.User;
import com.jornadamilhas.api.services.UserService;
import com.jornadamilhas.api.services.exceptions.NotValidException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    @Transactional
    public ResponseEntity<UserShowDto> create(@RequestBody @Valid UserCreateDto dto) {
        User user = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new UserShowDto(user));
    }

    @GetMapping
    public ResponseEntity<List<UserShowDto>> index() {
        List<UserShowDto> users = service.index();
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserShowDto> show(@PathVariable Long id) {
        User user = service.show(id);
        return ResponseEntity.ok(new UserShowDto(user));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
