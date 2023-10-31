package com.jornadamilhas.api.validations;

import com.jornadamilhas.api.dto.user.UserCreateDto;
import com.jornadamilhas.api.models.User;
import com.jornadamilhas.api.repositories.UserRepository;
import com.jornadamilhas.api.services.exceptions.NotValidException;
import org.hibernate.query.QueryTypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserEmailAlreadyRegisteredValidation implements CreateUserValidations {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(UserCreateDto dto) {

        boolean userExistByEmail = userRepository.existsByEmail(dto.email());
        if (userExistByEmail) {
            throw new NotValidException("Email já registrado para outro usuário");
        }
    }
}
