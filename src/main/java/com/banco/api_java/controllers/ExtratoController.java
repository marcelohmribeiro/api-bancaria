package com.banco.api_java.controllers;

import com.banco.api_java.dtos.ExtratoResponseDTO;
import com.banco.api_java.services.ExtratoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class ExtratoController {
    private final ExtratoService extratoService;

    public ExtratoController(ExtratoService extratoService) {
        this.extratoService = extratoService;
    }

    @GetMapping("/{accountNumber}/extrato")
    public ResponseEntity<List<ExtratoResponseDTO>> extrato(@PathVariable String accountNumber) {
        return ResponseEntity.ok(extratoService.gerarExtrato(accountNumber));
    }
}
