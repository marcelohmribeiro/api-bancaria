package com.banco.api_java.dtos;

import com.banco.api_java.enums.Role;
import com.banco.api_java.models.UserModel;

public record UserDTO(
        Long id,
        String email,
        String name,
        Role role
) {

    public UserDTO(UserModel user) {
        this(user.getId(), user.getEmail(), user.getName(), user.getRole());
    }
}
