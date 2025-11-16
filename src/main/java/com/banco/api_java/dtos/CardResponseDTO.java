package com.banco.api_java.dtos;

import com.banco.api_java.enums.Side;
import com.banco.api_java.enums.Status;

import java.time.LocalDate;

public record CardResponseDTO(
        Long id,
        String numeroMascarado,
        String ultimosDigitos,
        Side cardType,
        Status status,
        LocalDate expirationDate,
        String accountNumber
) {}
