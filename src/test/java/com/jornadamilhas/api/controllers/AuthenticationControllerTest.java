package com.jornadamilhas.api.controllers;

import com.jornadamilhas.api.dto.authentication.AuthenticationLoginDto;
import com.jornadamilhas.api.infra.security.TokenService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthenticationManager manager;

    @Mock
    Authentication authenticated;

    @MockBean
    private TokenService tokenService;

    @Autowired
    private JacksonTester<AuthenticationLoginDto> authDto;

    @Test
    @DisplayName("Deve retornar código 200 para solicitação de login com inputs válidos")
    void return200AuthLoginWithValidInputs() throws Exception {

        AuthenticationLoginDto dto = new AuthenticationLoginDto("teste", "123");
        String loginJson = authDto.write(dto).getJson();

        var authToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        given(manager.authenticate(authToken)).willReturn(authenticated);

        MockHttpServletResponse response = mvc.perform(
                post("/login")
                        .content(loginJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 400 para solicitação de login com inputs inválidos")
    void return400AuthLoginWithNotValidInputs() throws Exception {

        AuthenticationLoginDto dto = new AuthenticationLoginDto("teste", "");
        String loginJson = authDto.write(dto).getJson();

        MockHttpServletResponse response = mvc.perform(
                post("/login")
                        .content(loginJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }
}