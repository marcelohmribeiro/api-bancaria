package com.banco.api_java.controllers;

import com.banco.api_java.dtos.StatementResponseDTO;
import com.banco.api_java.services.StatementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statement")
public class StatementController {
    private final StatementService statementService;

    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<StatementResponseDTO>> extrato(@PathVariable String accountNumber) {
        return ResponseEntity.ok(statementService.gerarExtrato(accountNumber));
    }
}
