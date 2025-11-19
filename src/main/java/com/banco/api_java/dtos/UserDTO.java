package com.banco.api_java.dtos;

import com.banco.api_java.enums.Role;
import com.banco.api_java.models.UserModel;

public record UserDTO(
        Long id,
        String email,
        String name,
        Role role,
        AccountDTO account
) {
    public UserDTO(UserModel user) {
        this(
            user.getId(),
            user.getEmail(),
            user.getName(),
            user.getRole(),
            user.getAccount() != null ? new AccountDTO(
                    user.getAccount().getId(),
                    user.getAccount().getAgencyNumber(),
                    user.getAccount().getAccountNumber(),
                    user.getAccount().getBalance()
            ) : null
        );
    }
}
