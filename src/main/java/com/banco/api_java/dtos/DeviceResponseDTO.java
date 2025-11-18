package com.banco.api_java.dtos;

import java.time.LocalDateTime;

public record DeviceResponseDTO(
        Long id,
        String deviceName,
        String ip,
        String userAgent,
        LocalDateTime lastAccess
) {}
