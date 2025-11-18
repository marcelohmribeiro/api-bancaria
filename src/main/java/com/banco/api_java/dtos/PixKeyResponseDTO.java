package com.banco.api_java.dtos;

public record PixKeyResponseDTO(
        Long id,
        String type,
        String valueKey,
        String accountNumber,
        Long userId
) {}
