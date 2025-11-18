package com.banco.api_java.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InvestmentResponseDTO(
        Long id,
        String type,
        BigDecimal amount,
        BigDecimal currentAmount,
        BigDecimal monthlyRate,
        String status,
        String accountNumber,
        Long userId,
        LocalDateTime createdAt,
        LocalDateTime lastYieldDate
) {}