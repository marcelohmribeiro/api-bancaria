package com.banco.api_java.controllers;

import com.banco.api_java.dtos.TransactionDTO;
import com.banco.api_java.dtos.TransactionRequestDTO;
import com.banco.api_java.models.TransactionModel;
import com.banco.api_java.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> transfer(@RequestBody TransactionRequestDTO req) {
        TransactionModel tx = transactionService.transfer(
                req.fromAccountNumber(),
                req.toAccountNumber(),
                req.amount(),
                req.description()
        );
        return ResponseEntity.ok(new TransactionDTO(tx));
    }
}
