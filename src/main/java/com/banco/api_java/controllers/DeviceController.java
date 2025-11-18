package com.banco.api_java.controllers;

import com.banco.api_java.dtos.DeviceResponseDTO;
import com.banco.api_java.models.DeviceModel;
import com.banco.api_java.services.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeviceResponseDTO>> listarDispositivos(@PathVariable Long userId) {

        List<DeviceModel> dispositivos = deviceService.listarDispositivosDoUsuario(userId);

        List<DeviceResponseDTO> response = dispositivos.stream()
                .map(d -> new DeviceResponseDTO(
                        d.getId(),
                        d.getDeviceName(),
                        d.getIp(),
                        d.getUserAgent(),
                        d.getLastAccess()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }
}
