package com.banco.api_java.dtos;

import java.math.BigDecimal;

public record InvestmentCreateDTO(
        BigDecimal amount,
        BigDecimal monthlyRate,
        String type
) {}
