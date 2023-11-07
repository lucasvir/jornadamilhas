package com.jornadamilhas.api.controllers;

import com.jornadamilhas.api.dto.destiny.DestinyCreateDto;
import com.jornadamilhas.api.dto.destiny.DestinyShowDto;
import com.jornadamilhas.api.dto.destiny.DestinyUpdateDto;
import com.jornadamilhas.api.models.Destiny;
import com.jornadamilhas.api.services.DestinyService;
import com.jornadamilhas.api.services.exceptions.NotValidException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.InputMismatchException;
import java.util.List;

@RestController
@RequestMapping("destinos")
public class DestinyController {

    @Autowired
    private DestinyService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DestinyShowDto> create(@RequestBody @Valid DestinyCreateDto dto) {

        try {
            DestinyShowDto destiny = service.create(dto);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(destiny.id()).toUri();
            return ResponseEntity.created(uri).body(destiny);
        } catch (NotValidException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<DestinyShowDto>> index() {
        List<DestinyShowDto> destinations = service.index();

        return ResponseEntity.ok(destinations);
    }

    @GetMapping("{id}")
    public ResponseEntity<DestinyShowDto> show(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.show(id));
        } catch (NotValidException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<DestinyShowDto> update(@PathVariable Long id, @RequestBody DestinyUpdateDto dto) {

        try {
            DestinyShowDto destiny = service.update(id, dto);
            return ResponseEntity.ok(destiny);
        } catch (NotValidException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (InputMismatchException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NotValidException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("search")
    public ResponseEntity<List<DestinyShowDto>> search(@RequestParam("nome") String name) {
        try {
            List<DestinyShowDto> destinations = service.search(name);
            return ResponseEntity.ok(destinations);
        } catch (NotValidException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
