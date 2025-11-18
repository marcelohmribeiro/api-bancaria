package com.banco.api_java.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StatementResponseDTO(
        Long id,
        String tipo,
        BigDecimal valor,
        String descricao,
        LocalDateTime data,
        String lado
) {}
