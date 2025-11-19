package com.banco.api_java.dtos;

public record DepositDTO(
        String agency,
        String account,
        Double amount
) {}
