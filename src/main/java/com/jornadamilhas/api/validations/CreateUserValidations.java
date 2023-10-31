package com.jornadamilhas.api.validations;

import com.jornadamilhas.api.dto.user.UserCreateDto;

public interface CreateUserValidations {
    void validate(UserCreateDto dto);
}
