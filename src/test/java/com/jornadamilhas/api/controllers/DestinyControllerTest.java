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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
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
    private DestinyUpdateDto destinyUpdateDto;

    @Autowired
    private JacksonTester<DestinyCreateDto> destinyCreateDtoJson;

    @Autowired
    private JacksonTester<DestinyUpdateDto> destinyUpdateDtoJson;

    @Autowired
    private JacksonTester<DestinyShowDto> destinyShowDto;

    @Autowired
    private JacksonTester<List<DestinyShowDto>> listDestinyShowDto;

    // ############ CREATE ############
    @Test
    @DisplayName("Deve retornar 200 para solicitação de cadastrar destino com inputs válidos")
    @WithMockUser
    void return200DestinyCreateValidInputs() throws Exception {

        DestinyCreateDto dto = new DestinyCreateDto("São Paulo", "http://sajdiu.com, http://dasiud.com", "890,00", "dahiusdhia",
                "aiudshiashidashdisa");
        String createJson = destinyCreateDtoJson.write(dto).getJson();

        var showDto = new DestinyShowDto(
                1L,
                "Sao Paulo",
                "http://sajdiu.com, http://dasiud.com",
                "dahiusdhia",
                "dahiusdhia"
        );
        String showJson = destinyShowDto.write(showDto).getJson();


        when(service.create(dto)).thenReturn(showDto);

        var response = mvc.perform(
                        post("/destinos")
                                .content(createJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(showJson, response.getContentAsString());
        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 201 para solicitação de cadastrar destino com inputs válidos sem informar a descriação")
    @WithMockUser
    void return201DestinyCreateValidInputsWithIADescription() throws Exception {

        DestinyCreateDto dto = new DestinyCreateDto("teste", "http://ahdusihi.com, http://ipqpeqo.com, http://shdiqii.com", "hasidhuiq");
        String createJson = destinyCreateDtoJson.write(dto).getJson();

        var showDto = new DestinyShowDto(
                1L,
                "Sao Paulo",
                "http://sajdiu.com, http://dasiud.com",
                "dahiusdhia",
                "dahiusdhia"
        );

        String showJson = destinyShowDto.write(showDto).getJson();

        when(service.create(dto)).thenReturn(showDto);

        var response = mvc.perform(
                        post("/destinos")
                                .content(createJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(showJson, response.getContentAsString());
        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 400 para solicitação de cadastrar destino com inputs não válidos")
    @WithMockUser
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
    @WithMockUser
    void return400DestinyCreateSameInputs() throws Exception {

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
        var showDto = new DestinyShowDto(
                1L,
                "Sao Paulo",
                "http://sajdiu.com, http://dasiud.com",
                "aiushida",
                "dahiusdhia"
        );
        String showJson = destinyShowDto.write(showDto).getJson();

        when(service.show(1L)).thenReturn(showDto);

        var response = mvc.perform(get("/destinos/1"))
                .andReturn().getResponse();

        assertEquals(showJson, response.getContentAsString());
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 404 para solicitação de mostrar destino por id não válido")
    void return404DestinyShowNotValidId() throws Exception {

        given(service.show(1L)).willThrow(EntityNotFoundException.class);

        var response = mvc.perform(get("/destinos/1"))
                .andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    // ############ UPDATE ############
    @Test
    @DisplayName("Deve retornar 200 para solicitação para atualizar destino por id válido")
    @WithMockUser
    void return200DestinyUpdateValidId() throws Exception {

        DestinyUpdateDto updateDto = new DestinyUpdateDto(
                "Sao Paulo",
                "http://sajdiu.com, http://dasiud.com",
                "890,00",
                "dahiusdhia",
                "dahiusdhia"
        );
        String updateJson = destinyUpdateDtoJson.write(updateDto).getJson();

        var showDto = new DestinyShowDto(
                1L,
                "Sao Paulo",
                "http://sajdiu.com, http://dasiud.com",
                "dahiusdhia",
                "dahiusdhia"
        );
        String showJson = destinyShowDto.write(showDto).getJson();

        given(service.update(1L, updateDto)).willReturn(showDto);

        var response = mvc.perform(
                        put("/destinos/1")
                                .content(updateJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(showJson, response.getContentAsString());
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 404 para solicitação para atualizar destino por id não válido")
    @WithMockUser
    void return404DestinyUpdateNotValidId() throws Exception {

        DestinyUpdateDto dto = new DestinyUpdateDto("teste", "http://teste.com", "350,00", "huiashdiasids", "saiudiqw");
        String json = destinyUpdateDtoJson.write(dto).getJson();

        given(service.update(1l, dto)).willThrow(EntityNotFoundException.class);

        var response = mvc.perform(
                        put("/destinos/1")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 400 para solicitação para atualizar destino por id válido e nome já existente")
    @WithMockUser
    void return400DestinyUpdateValidIdNotValidInputs() throws Exception {

        DestinyUpdateDto dto = new DestinyUpdateDto("teste", "http://teste.com", "350,00", "aosdsa", "iudhsaiidsa");
        String json = destinyUpdateDtoJson.write(dto).getJson();

        given(service.update(1L, dto)).willThrow(RuntimeException.class);

        var response = mvc.perform(
                        put("/destinos/1")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 400 para solicitação para atualizar destino por id válido e inputs não preenchidos")
    @WithMockUser
    void return400DestinyUpdateValidIdInputsNotSeted() throws Exception {

        DestinyUpdateDto dto = new DestinyUpdateDto("", "", "", "", "");
        String json = destinyUpdateDtoJson.write(dto).getJson();

        given(service.update(1l, dto)).willThrow(InputMismatchException.class);

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

        var showDto = new DestinyShowDto(
                1L,
                "Sao Paulo",
                "http://sajdiu.com, http://dasiud.com",
                "dahiusdhia",
                "dahiusdhia"
        );
        String showJson = destinyShowDto.write(showDto).getJson();

        List<DestinyShowDto> list = new ArrayList<>();
        list.add(showDto);
        var listJson = listDestinyShowDto.write(list).getJson();

        given(service.search("rio")).willReturn(list);

        var response = mvc.perform(get("/destinos/search?nome=rio"))
                .andReturn()
                .getResponse();

        assertEquals(listJson, response.getContentAsString());
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 400 para solicitação de busca de destino por nome em branco")
    void return400DestinySearchEmptyInput() throws Exception {

        given(service.search("")).willThrow(RuntimeException.class);

        var response = mvc.perform(get("/destinos/search?nome="))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 404 para solicitação de busca de destino por nome não existente")
    void return400DestinySearchNameNotExists() throws Exception {

        given(service.search("rio")).willThrow(EntityNotFoundException.class);

        var response = mvc.perform(get("/destinos/search?nome=rio"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    // ############ DELETE ############
    @Test
    @DisplayName("Deve retornar 204 para solicitação de deletar destino por id válido")
    @WithMockUser
    void return204DestinyDeleteValidId() throws Exception {

        var response = mvc.perform(delete("/destinos/1"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar 404 para solicitação de deletar destino por id não válido")
    @WithMockUser
    void return404DestinyDeleteNotValidId() throws Exception {

        doThrow(EntityNotFoundException.class).when(service).delete(1L);

        var response = mvc.perform(delete("/destinos/1"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }
}
