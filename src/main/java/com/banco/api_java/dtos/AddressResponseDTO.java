package com.banco.api_java.dtos;

public record AddressResponseDTO(
        Long id,
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep,
        Long userId
) {}