package com.banco.api_java.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRecordDto(@NotBlank String name, @NotBlank @Email String email) {
}
