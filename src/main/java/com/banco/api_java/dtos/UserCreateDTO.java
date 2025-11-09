package com.banco.api_java.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
        @NotBlank @Size(min = 2, max = 70) String name,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, max = 72) String password
) {}
