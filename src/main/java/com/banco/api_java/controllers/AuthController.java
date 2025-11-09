package com.banco.api_java.controllers;

import com.banco.api_java.dtos.AuthResponse;
import com.banco.api_java.dtos.LoginDTO;
import com.banco.api_java.services.UserService;
import com.banco.api_java.utils.JwtUtil;
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
    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDTO loginDTO) {
        var userOpt = userService.login(loginDTO.email(), loginDTO.password());
        if(userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var user = userOpt.get();
        var token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(user, token));
    }
}
