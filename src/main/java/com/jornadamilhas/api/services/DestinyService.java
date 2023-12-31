package com.jornadamilhas.api.services;

import com.jornadamilhas.api.ChatGPTApi;
import com.jornadamilhas.api.dto.destiny.DestinyCreateDto;
import com.jornadamilhas.api.dto.destiny.DestinyShowDto;
import com.jornadamilhas.api.dto.destiny.DestinyUpdateDto;
import com.jornadamilhas.api.models.Destiny;
import com.jornadamilhas.api.repositories.DestinyRepository;
import com.jornadamilhas.api.services.exceptions.NotValidException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

@Service
public class DestinyService {

    @Autowired
    private DestinyRepository repository;

    public DestinyShowDto create(DestinyCreateDto dto) {

        boolean destinyExists = repository.existsByNameIgnoreCase(dto.name());
        if (destinyExists) {
            throw new NotValidException("Destino já cadastrado com esse nome. Nome -> " + dto.name(), HttpStatus.BAD_REQUEST);
        }

        Destiny destiny = new Destiny(dto);

        if (dto.description() == null || dto.description().isEmpty()) {
            String prompt = "Faça um resumo sobre " + dto.name() + " enfatizando o porque este lugar é incrível. "
                    + "Crie 1 parágrafos utilizando uma linguagem informal de até 250 caracteres.";

            String chatGptDescription = ChatGPTApi.chatGPT(prompt);
            System.out.println(chatGptDescription);
            destiny.setDescription(chatGptDescription);
        }

        repository.save(destiny);

        return new DestinyShowDto(destiny);
    }

    public List<DestinyShowDto> index() {
        List<Destiny> destinyList = repository.findAll();
        List<DestinyShowDto> destinations = new ArrayList<>();

        destinyList.forEach(d -> {
            var destiny = new DestinyShowDto(d);
            destinations.add(destiny);
        });

        return destinations;
    }

    public DestinyShowDto show(Long id) {
        Destiny destiny = repository.getReferenceById(id);
        return new DestinyShowDto(destiny);
    }

    public void delete(Long id) {
        show(id); //id validation

        repository.deleteById(id);
    }

    public DestinyShowDto update(Long id, DestinyUpdateDto dto) {
        Destiny destiny = repository.getReferenceById(id);

        boolean existsByName = repository.existsByNameIgnoreCase(dto.name());
        if (existsByName) {
            throw new NotValidException("Destino já registrado com esse nome. Nome -> " + dto.name(), HttpStatus.BAD_REQUEST);
        }

        var dtoIsEmpty = dto.name() == null && dto.imgs() == null && dto.price() == null && dto.meta() == null;
        if (dtoIsEmpty) {
            throw new RuntimeException("Nenhum campo foi preenchido para fazer a atualização.");
        }

        destiny.updateData(dto);
        repository.save(destiny);

        return new DestinyShowDto(destiny);
    }

    public List<DestinyShowDto> search(String nome) {
        if (!nome.equalsIgnoreCase("")) {
            List<Destiny> detinations = repository.findByNameContainsIgnoreCase(nome);
            if (detinations.isEmpty()) {
                throw new RuntimeException("Nenhum destino foi encontrado.");
            }

            List<DestinyShowDto> destinyShowDtos = new ArrayList<>();
            detinations.forEach(d -> destinyShowDtos.add(new DestinyShowDto(d)));


            return destinyShowDtos;

        } else {
            throw new EntityNotFoundException("Nome para busca não foi informado.");
        }
    }
}
