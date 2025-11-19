package com.banco.api_java.dtos;

import java.math.BigDecimal;

public record AccountDTO(
        Long id,
        String agencyNumber,
        String accountNumber,
        BigDecimal balance
) {}
