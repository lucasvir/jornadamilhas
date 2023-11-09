package com.jornadamilhas.api.controllers;

import com.jornadamilhas.api.dto.comment.CommentCreateDto;
import com.jornadamilhas.api.dto.comment.CommentShowDto;
import com.jornadamilhas.api.dto.comment.CommentUpdateDto;
import com.jornadamilhas.api.models.Comment;
import com.jornadamilhas.api.models.User;
import com.jornadamilhas.api.services.CommentService;
import com.jornadamilhas.api.services.exceptions.NotValidException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService service;

    @Mock
    private Comment comment;

    @Mock
    private User user;

    @Autowired
    private JacksonTester<CommentCreateDto> commentCreateDto;

    @Autowired
    private JacksonTester<CommentUpdateDto> commentUpdateDto;

    @Autowired
    private JacksonTester<CommentShowDto> commentShowDto;
    @Mock
    private List<CommentShowDto> commentsShowList;

    //CREATE

    @Test
    @DisplayName("Deve retornar código 201 para solicitação com inputs válidos")
    void return201CommentCreatedValidInput() throws Exception {

        CommentCreateDto dto = new CommentCreateDto("aiushdiashida");
        String createJson = commentCreateDto.write(dto).getJson();

        var user = new User(
                1L,
                "lucas",
                "lucas@email.com",
                "http://asdwq.com",
                "123",
                "123",
                null
        );

        Comment comment = new Comment(dto, user);
        CommentShowDto showDto = new CommentShowDto(comment);
        String showJson = commentShowDto.write(showDto).getJson();

        given(service.create(1L, dto)).willReturn(showDto);

        var response = mvc.perform(
                        post("/depoimentos/1")
                                .content(createJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        assertEquals(showJson, response.getContentAsString());
        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 400, inputs não válidos")
    void return400CommentCreatedNotValidInput() throws Exception {
        var response = mvc.perform(
                        post("/depoimentos/1")
                )
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 404, inputs válidos mas id não existe")
    void return404CommentCreatedValidInputNotValidId() throws Exception {
        CommentCreateDto dto = new CommentCreateDto("aiushdiashida");
        String json = commentCreateDto.write(dto).getJson();

        when(service.create(1l, dto)).thenThrow(NotValidException.class);

        var response = mvc.perform(
                        post("/depoimentos/1/create")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    // INDEX

    @Test
    @DisplayName("Deve retornar código 200 para solicitação de listar comentários ")
    void return200CommentIndex() throws Exception {

        var response = mvc.perform(get("/depoimentos"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    //SHOW
    @Test
    @DisplayName("Deve retornar código 200 para solicitação de mostrar comentário por id (válido)")
    void return200CommentShow() throws Exception {

        CommentCreateDto dto = new CommentCreateDto("aiushdiashida");
        String createJson = commentCreateDto.write(dto).getJson();

        var user = new User(
                1L,
                "lucas",
                "lucas@email.com",
                "http://asdwq.com",
                "123",
                "123",
                null
        );

        Comment comment = new Comment(dto, user);
        CommentShowDto showDto = new CommentShowDto(comment);
        String showJson = commentShowDto.write(showDto).getJson();

        when(service.show(1l)).thenReturn(showDto);

        var response = mvc.perform(get("/depoimentos/1"))
                .andReturn()
                .getResponse();

        assertEquals(showJson, response.getContentAsString());
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 404 para solicitação de mostrar comentário por id (não válido)")
    void return404CommentShowNotValidId() throws Exception {

        when(service.show(1L)).thenThrow(EntityNotFoundException.class);

        var response = mvc.perform(get("/depoimentos/1"))
                .andReturn()
                .getResponse();

        assertEquals(404, response.getStatus());
    }

    //UPDATE
    @Test
    @DisplayName("Deve retornar código 200 para solicitação de atualizar comentário por id (válido) e inputs válidos")
    @WithMockUser
    void return200CommentUpdateValidId() throws Exception {

        CommentUpdateDto dto = new CommentUpdateDto("haiuhsida");
        var updateJson = commentUpdateDto.write(dto).getJson();

        CommentShowDto showDto = new CommentShowDto(1L, "haiuhsida", "lucas", "http://teste.com");
        String showJson = commentShowDto.write(showDto).getJson();

        when(service.update(1L, dto)).thenReturn(showDto);

        var response = mvc.perform(
                put("/depoimentos/1")
                        .content(updateJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(showJson, response.getContentAsString());
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 400 para solicitação de atualizar comentário por id (válido) e inputs não válidos")
    @WithMockUser
    void return400CommentUpdateValidIdNotValidInputs() throws Exception {
        var response = mvc.perform(
                put("/depoimentos/1")
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 404 para solicitação de atualizar comentário por id (não válido) e inputs válidos")
    @WithMockUser
    void return404CommentUpdateNotValidIdValidInputs() throws Exception {

        CommentUpdateDto dto = new CommentUpdateDto("sdqsdqwdqda");
        var json = commentUpdateDto.write(dto).getJson();

        when(service.update(1L, dto)).thenThrow(EntityNotFoundException.class);

        var response = mvc.perform(
                put("/depoimentos/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    //DELETE
    @Test
    @DisplayName("Deve retornar código 204 para solicitação de deletar comentário por id (válido)")
    @WithMockUser
    void return204CommentDeleteValidId() throws Exception {

        var response = mvc.perform(
                delete("/depoimentos/1")
        ).andReturn().getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 404 para solicitação de deletar comentário por id (não válido)")
    @WithMockUser
    void return404CommentDeleteNotValidId() throws Exception {

        doThrow(EntityNotFoundException.class).when(service).delete(1L);

        var response = mvc.perform(
                delete("/depoimentos/1")
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }


    //RANDOM COMMENTS
    @Test
    @DisplayName("Deve retornar código 200 para solicitação de listar três comentários randomicos")
    void return200CommentRandom() throws Exception {

        var response = mvc.perform(
                get("/depoimentos/depoimentos-home")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }
}