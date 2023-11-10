package com.jornadamilhas.api.controllers;

import com.jornadamilhas.api.dto.user.UserCreateDto;
import com.jornadamilhas.api.dto.user.UserShowDto;
import com.jornadamilhas.api.models.User;
import com.jornadamilhas.api.repositories.UserRepository;
import com.jornadamilhas.api.services.UserService;
import com.jornadamilhas.api.services.exceptions.NotValidException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Mock
    private User user;

    @Mock
    private UserShowDto userDto;

    @Mock
    private List<User> userList;

    @Mock
    private List<UserShowDto> userDtos;

    @Autowired
    private JacksonTester<UserCreateDto> createDto;

    @Autowired
    private JacksonTester<UserShowDto> showDto;


    //CREATE
    @Test
    @DisplayName("Deve retornar codigo 201, inputs no corpo são válidos")
    void return201UserCreatedValidInputs() throws Exception {

        var dto = new UserCreateDto(
                "lucas",
                "lucas@email.com",
                "http://asdwq.com",
                "123"
        );


        var userShow = new User(
                1L,
                "lucas",
                "lucas@email.com",
                "http://asdwq.com",
                "123",
                "123",
                null
        );


        String json = createDto.write(dto).getJson();
        String expectedJson = showDto.write(new UserShowDto(userShow)).getJson();

        given(userService.create(dto)).willReturn(userShow);

        var response = mvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();


        assertEquals(expectedJson, response.getContentAsString());
        assertEquals(201, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar codigo 400, inputs no corpo não válidos")
    void return400UserCreatedNotValidInputs() throws Exception {

        var dto = new UserCreateDto(
                "", //not valid input
                "lucas@email.com",
                "http://asdwq.com",
                "123"
        );

        String json = createDto.write(dto).getJson();

        given(userService.create(dto)).willReturn(user);

        MockHttpServletResponse response = mvc.perform(
                post("/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        Assertions.assertEquals(400, response.getStatus());
    }

    // INDEX

    @Test
    @DisplayName("Deve retornar código 200 para solicitação index")
    void return200Index() throws Exception {

        var response = mvc.perform(get("/users"))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    // SHOW

    @Test
    @DisplayName("Deve retornar código 200 para solicitação de mostar um usuario especifico por id válido")
    void return200ShowUserByValidId() throws Exception {

        var userShow = new User(
                1L,
                "lucas",
                "lucas@email.com",
                "http://asdwq.com",
                "123",
                "123",
                null
        );
        String expectedJson = showDto.write(new UserShowDto(userShow)).getJson();

        given(userService.show(1L)).willReturn(userShow);

        var response = mvc.perform(get("/users/1"))
                .andReturn().getResponse();

        assertEquals(expectedJson, response.getContentAsString());
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 404 para solicitação de mostar um usuario especifico por id inválido")
    void return404ShowUserByNotValidId() throws Exception {

        given(userService.show(1l)).willThrow(EntityNotFoundException.class);

        var response = mvc.perform(get("/users/1"))
                .andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    //DELETE
    @Test
    @DisplayName("Deve retornar código 204 para solicitação de deletar um usuario específico por id válido")
    @WithMockUser
    void return200DeleteUserByValidId() throws Exception {
        var response = mvc.perform(delete("/users/1"))
                .andReturn().getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 404 para solicitação de deletar um usuario específico por id não válido")
    @WithMockUser
    void return404DeleteUserByNotValidId() throws Exception {

        doThrow(EntityNotFoundException.class).when(userService).delete(1L);

        var response = mvc.perform(delete("/users/1"))
                .andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }
}