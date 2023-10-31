package com.jornadamilhas.api.services;

import com.jornadamilhas.api.dto.user.UserCreateDto;
import com.jornadamilhas.api.models.User;
import com.jornadamilhas.api.repositories.UserRepository;
import com.jornadamilhas.api.services.exceptions.NotValidException;
import com.jornadamilhas.api.validations.CreateUserValidations;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<User> index() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User show(Long id) {
        try {
            return userRepository.getReferenceById(id);
        } catch (EntityNotFoundException e) {
            throw new NotValidException("Usuário não encontrado. Id -> " + id);
        }
    }
}
