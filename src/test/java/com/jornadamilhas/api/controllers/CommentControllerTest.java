package com.jornadamilhas.api.controllers;

import com.jornadamilhas.api.dto.comment.CommentCreateDto;
import com.jornadamilhas.api.dto.comment.CommentShowDto;
import com.jornadamilhas.api.dto.comment.CommentUpdateDto;
import com.jornadamilhas.api.models.Comment;
import com.jornadamilhas.api.models.User;
import com.jornadamilhas.api.services.CommentService;
import com.jornadamilhas.api.services.exceptions.NotValidException;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
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

    @Mock
    private CommentShowDto commentShowDto;

    @Autowired
    private JacksonTester<CommentCreateDto> commentCreateDto;

    @Autowired
    private JacksonTester<CommentUpdateDto> commentUpdateDto;

    @Mock
    private List<CommentShowDto> commentsShowList;

    //CREATE

    @Test
    @DisplayName("Deve retornar código 201, inputs válidos")
    void return201CommentCreatedValidInput() throws Exception {
        CommentCreateDto dto = new CommentCreateDto("aiushdiashida");
        String json = commentCreateDto.write(dto).getJson();

        given(service.create(1l, dto)).willReturn(commentShowDto);

        var response = mvc.perform(
                        post("/depoimentos/1/create")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 400, inputs não válidos")
    void return400CommentCreatedNotValidInput() throws Exception {
        CommentCreateDto dto = new CommentCreateDto("");
        String json = commentCreateDto.write(dto).getJson();

        given(service.create(1l, dto)).willThrow(NotValidException.class);

        var response = mvc.perform(
                        post("/depoimentos/1/create")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 400, inputs válidos mas id não existe")
    void return400CommentCreatedValidInputNotValidId() throws Exception {
        CommentCreateDto dto = new CommentCreateDto("aiushdiashida");
        String json = commentCreateDto.write(dto).getJson();

        when(service.create(1l, dto)).thenThrow(NotValidException.class);

        var response = mvc.perform(
                        post("/depoimentos/1/create")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
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

        when(service.show(1l)).thenReturn(commentShowDto);

        var response = mvc.perform(get("/depoimentos/1"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 400 para solicitação de mostrar comentário por id (não válido)")
    void return400CommentShowNotValidId() throws Exception {

        when(service.show(1l)).thenThrow(NotValidException.class);

        var response = mvc.perform(get("/depoimentos/1"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    //UPDATE
    @Test
    @DisplayName("Deve retornar código 200 para solicitação de atualizar comentário por id (válido) e inputs válidos")
    void return200CommentUpdateValidId() throws Exception {

        CommentUpdateDto dto = new CommentUpdateDto("haiuhsida");
        var json = commentUpdateDto.write(dto).getJson();

        when(service.update(1l, dto)).thenReturn(commentShowDto);

        var response = mvc.perform(
                put("/depoimentos/1/update")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 400 para solicitação de atualizar comentário por id (válido) e inputs não válidos")
    void return400CommentUpdateValidIdNotValidInputs() throws Exception {

        CommentUpdateDto dto = new CommentUpdateDto("");
        var json = commentUpdateDto.write(dto).getJson();

        when(service.update(1l, dto)).thenThrow(NotValidException.class);

        var response = mvc.perform(
                put("/depoimentos/1/update")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 400 para solicitação de atualizar comentário por id (não válido) e inputs válidos")
    void return400CommentUpdateNotValidIdValidInputs() throws Exception {

        CommentUpdateDto dto = new CommentUpdateDto("sdqsdqwdqda");
        var json = commentUpdateDto.write(dto).getJson();

        when(service.update(1l, dto)).thenThrow(NotValidException.class);

        var response = mvc.perform(
                put("/depoimentos/1/update")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    //DELETE
    @Test
    @DisplayName("Deve retornar código 204 para solicitação de deletar comentário por id (válido)")
    void return204CommentDeleteValidId() throws Exception {

        var response = mvc.perform(
                delete("/depoimentos/1/delete")
        ).andReturn().getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

//    @Test
//    @DisplayName("Deve retornar código 400 para solicitação de deletar comentário por id (não válido)")
//    void return400CommentDeleteNotValidId() throws Exception {
//
//        var response = mvc.perform(
//                delete("/depoimentos/1/delete")
//        ).andReturn().getResponse();
//
//        Assertions.assertEquals(204, response.getStatus());
//    }


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