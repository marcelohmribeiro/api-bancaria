package com.banco.api_java.dtos;

public record AddressRequestDTO(
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep
) {}