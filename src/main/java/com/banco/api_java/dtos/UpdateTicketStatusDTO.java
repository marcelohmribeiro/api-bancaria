package com.banco.api_java.dtos;

import com.banco.api_java.enums.TicketStatus;

public record UpdateTicketStatusDTO(
        TicketStatus status
) {}
