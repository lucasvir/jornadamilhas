package com.jornadamilhas.api.controllers;

import com.jornadamilhas.api.dto.destiny.DestinyCreateDto;
import com.jornadamilhas.api.dto.destiny.DestinyShowDto;
import com.jornadamilhas.api.dto.destiny.DestinyUpdateDto;
import com.jornadamilhas.api.models.Destiny;
import com.jornadamilhas.api.repositories.DestinyRepository;
import com.jornadamilhas.api.services.DestinyService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.InputMismatchException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DestinyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DestinyService service;

    @Mock
    private List<DestinyShowDto> showDtoList;

    @Mock
    private Destiny destiny;

    @Mock
    private DestinyCreateDto destinyCreateDto;

    @Mock
    private DestinyShowDto destinyShowDto;

    @Mock
    private DestinyUpdateDto destinyUpdateDto;

    @Autowired
    private JacksonTester<DestinyCreateDto> destinyCreateDtoJson;

    @Autowired
    private JacksonTester<DestinyUpdateDto> destinyUpdateDtoJson;

    // ############ CREATE ############
    @Test
    @DisplayName("Deve retornar 200 para solicitação de cadastrar destino com inputs válidos")
    void return200DestinyCreateValidInputs() throws Exception {

        DestinyCreateDto dto = new DestinyCreateDto("São Paulo", "http://sajdiu.com", "890,00", "dahiusdhia",
                "aiudshiashidashdisa");
        String json = destinyCreateDtoJson.write(dto).getJson();

        when(service.create(dto)).thenReturn(destinyShowDto);

        var response = mvc.perform(
                post("/destinos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 201 para solicitação de cadastrar destino com inputs válidos sem informar a descriação")
    void return201DestinyCreateValidInputsWithIADescription() throws Exception {

        DestinyCreateDto dto = new DestinyCreateDto("teste", "http://ahdusihi.com, http://ipqpeqo.com, http://shdiqii.com", "hasidhuiq");
        String json = destinyCreateDtoJson.write(dto).getJson();

        when(service.create(dto)).thenReturn(destinyShowDto);

        var response = mvc.perform(
                post("/destinos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 400 para solicitação de cadastrar destino com inputs não válidos")
    void return400DestinyCreateNotValidInputs() throws Exception {

        DestinyCreateDto dto = new DestinyCreateDto("", "http://sajdiu.com", "890,00", "hauisdisa", "pppowqpoqwop");
        String json = destinyCreateDtoJson.write(dto).getJson();

        when(service.create(dto)).thenThrow(NotValidException.class);

        var response = mvc.perform(
                post("/destinos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 400 para solicitação de cadastrar destino com inputs já cadastrados")
    void return400DestinyCreateSameInputs() throws Exception {

        // DestinyCreateDto dto = new DestinyCreateDto("São Paulo", "http://sajdiu.com",
        // "890,00");
        String json = destinyCreateDtoJson.write(destinyCreateDto).getJson();

        when(service.create(destinyCreateDto)).thenThrow(NotValidException.class);

        var response = mvc.perform(
                post("/destinos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    // ############ INDEX ############

    @Test
    @DisplayName("Deve retornar 200 para solicitação de listar destinos")
    void return200DestinyIndex() throws Exception {

        var response = mvc.perform(get("/destinos"))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    // ############ SHOW ############
    @Test
    @DisplayName("Deve retornar 200 para solicitação de mostrar destino por id válido")
    void return200DestinyShowValidId() throws Exception {
        when(service.show(1l)).thenReturn(destinyShowDto);

        var response = mvc.perform(get("/destinos/1"))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 404 para solicitação de mostrar destino por id não válido")
    void return404DestinyShowNotValidId() throws Exception {

        BDDMockito.given(service.show(1l)).willThrow(NotValidException.class);

        var response = mvc.perform(get("/destinos/1"))
                .andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    // ############ UPDATE ############
    @Test
    @DisplayName("Deve retornar 200 para solicitação para atualizar destino por id válido")
    void return200DestinyUpdateValidId() throws Exception {

        // DestinyUpdateDto dto = new DestinyUpdateDto("teste", "http://teste.com",
        // "350,00", "haiushdias", "oijoijqowijdq");
        String json = destinyUpdateDtoJson.write(destinyUpdateDto).getJson();

        BDDMockito.given(service.update(1l, destinyUpdateDto)).willReturn(destinyShowDto);

        var response = mvc.perform(
                put("/destinos/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 404 para solicitação para atualizar destino por id não válido")
    void return404DestinyUpdateNotValidId() throws Exception {

        DestinyUpdateDto dto = new DestinyUpdateDto("teste", "http://teste.com", "350,00", "huiashdiasids", "saiudiqw");
        String json = destinyUpdateDtoJson.write(dto).getJson();

        BDDMockito.given(service.update(1l, dto)).willThrow(EntityNotFoundException.class);

        var response = mvc.perform(
                put("/destinos/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 400 para solicitação para atualizar destino por id válido e nome já existente")
    void return400DestinyUpdateValidIdNotValidInputs() throws Exception {

        DestinyUpdateDto dto = new DestinyUpdateDto("teste", "http://teste.com", "350,00", "aosdsa", "iudhsaiidsa");
        String json = destinyUpdateDtoJson.write(dto).getJson();

        BDDMockito.given(service.update(1l, dto)).willThrow(NotValidException.class);

        var response = mvc.perform(
                put("/destinos/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 400 para solicitação para atualizar destino por id válido e inputs não preenchidos")
    void return400DestinyUpdateValidIdInputsNotSeted() throws Exception {

        DestinyUpdateDto dto = new DestinyUpdateDto("", "", "", "", "");
        String json = destinyUpdateDtoJson.write(dto).getJson();

        BDDMockito.given(service.update(1l, dto)).willThrow(InputMismatchException.class);

        var response = mvc.perform(
                put("/destinos/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    // ############ SEARCH ############
    @Test
    @DisplayName("Deve retornar 200 para solicitação de busca de destino por nome válido")
    void return200DestinySearchValidName() throws Exception {

        var response = mvc.perform(get("/destinos/search?nome=rio"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 400 para solicitação de busca de destino por nome em branco")
    void return400DestinySearchEmptyInput() throws Exception {

        BDDMockito.given(service.search("")).willThrow(NotValidException.class);

        var response = mvc.perform(get("/destinos/search?nome="))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 400 para solicitação de busca de destino por nome não existente")
    void return400DestinySearchNameNotExists() throws Exception {

        BDDMockito.given(service.search("rio")).willThrow(NotValidException.class);

        var response = mvc.perform(get("/destinos/search?nome=rio"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    // ############ SEARCH ############
    @Test
    @DisplayName("Deve retornar 204 para solicitação de deletar destino por id válido")
    void return204DestinyDeleteValidId() throws Exception {

        var response = mvc.perform(delete("/destinos/1"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 404 para solicitação de deletar destino por id não válido")
    void return404DestinyDeleteNotValidId() throws Exception {

        doThrow(NotValidException.class).when(service).delete(1l);

        var response = mvc.perform(delete("/destinos/1"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }
}
