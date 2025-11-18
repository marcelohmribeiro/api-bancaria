package com.banco.api_java.controllers;

import com.banco.api_java.dtos.AuthResponse;
import com.banco.api_java.dtos.LoginDTO;
import com.banco.api_java.services.DeviceService;
import com.banco.api_java.services.UserService;
import com.banco.api_java.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final DeviceService deviceService;
    public AuthController(UserService userService, JwtUtil jwtUtil, DeviceService deviceService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.deviceService = deviceService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        var userOpt = userService.login(loginDTO.email(), loginDTO.password());
        if(userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var user = userOpt.get();
        // gera token
        var token = jwtUtil.generateToken(user);
        // dados do dispositivo
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String deviceName = userAgent != null ? userAgent : "Desconhecido";
        deviceService.registrarAcesso(
                user.id(),
                deviceName,
                ip,
                userAgent
        );
        return ResponseEntity.ok(new AuthResponse(user, token));
    }
}
