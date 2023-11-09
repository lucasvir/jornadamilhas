package com.jornadamilhas.api.controllers;

import com.jornadamilhas.api.dto.authentication.AuthenticationLoginDto;
import com.jornadamilhas.api.infra.security.TokenJwtDto;
import com.jornadamilhas.api.infra.security.TokenService;
import com.jornadamilhas.api.models.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity login(@RequestBody @Valid AuthenticationLoginDto dto) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
            var authenticated = manager.authenticate(authToken);

            var tokenJWT = tokenService.generateToken((User) authenticated.getPrincipal());
            return ResponseEntity.ok(new TokenJwtDto(tokenJWT));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
