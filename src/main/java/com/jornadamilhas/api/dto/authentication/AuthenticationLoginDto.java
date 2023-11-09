package com.jornadamilhas.api.dto.authentication;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationLoginDto(@NotBlank String login, @NotBlank String password) {
}
