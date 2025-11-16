package com.banco.api_java.dtos;

import com.banco.api_java.enums.TicketStatus;

import java.time.LocalDateTime;

public record SupportTicketResponseDTO(
        Long id,
        Long userId,
        String description,
        String category,
        TicketStatus status,
        LocalDateTime createdAt
) {}
