package com.banco.api_java.dtos;

public record CardRequestDTO(
        String accountNumber,
        String cardType
) {}
