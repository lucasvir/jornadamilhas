package com.jornadamilhas.api.services;

import com.jornadamilhas.api.dto.user.UserCreateDto;
import com.jornadamilhas.api.dto.user.UserShowDto;
import com.jornadamilhas.api.models.User;
import com.jornadamilhas.api.repositories.UserRepository;
import com.jornadamilhas.api.services.exceptions.NotValidException;
import com.jornadamilhas.api.validations.CreateUserValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    List<CreateUserValidations> validations;

    public User create(UserCreateDto dto) {
        validations.forEach(v -> v.validate(dto));

        return userRepository.save(new User(dto));
    }

    public List<UserShowDto> index() {
        List<User> users = userRepository.findAll();
        List<UserShowDto> usersDto = new ArrayList<>();

        users.forEach(u -> {
            var dto = new UserShowDto(u);
            usersDto.add(dto);
        });

        return usersDto;
    }

    public User show(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotValidException("Usuário não encontrado. Id -> " + id));
    }

    public void delete(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotValidException("Usuário não encontrado. Id -> " + id)); //cheking id exists

        userRepository.deleteById(id);
    }
}
