package com.banco.api_java.dtos;

import java.math.BigDecimal;

public record TransactionRequestDTO(
        String fromAccountNumber,
        String toAccountNumber,
        BigDecimal amount,
        String description
) {}