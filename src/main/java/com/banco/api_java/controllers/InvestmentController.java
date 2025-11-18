package com.banco.api_java.controllers;

import com.banco.api_java.dtos.InvestmentCreateDTO;
import com.banco.api_java.dtos.InvestmentResponseDTO;
import com.banco.api_java.models.InvestmentModel;
import com.banco.api_java.services.InvestmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/investments")
public class InvestmentController {
    private final InvestmentService investmentService;
    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    // Criar investimento para um usuário
    @PostMapping("/user/{userId}")
    public ResponseEntity<InvestmentResponseDTO> criarInvestimento(
            @PathVariable Long userId,
            @RequestBody InvestmentCreateDTO dto
    ) {
        InvestmentModel inv = investmentService.criarInvestimento(
                userId,
                dto.amount(),
                dto.monthlyRate(),
                dto.type()
        );

        InvestmentResponseDTO response = new InvestmentResponseDTO(
                inv.getId(),
                inv.getType(),
                inv.getAmount(),
                inv.getCurrentAmount(),
                inv.getMonthlyRate(),
                inv.getStatus(),
                inv.getAccount().getAccountNumber(),
                inv.getAccount().getUser().getId(),
                inv.getCreatedAt(),
                inv.getLastYieldDate()
        );

        return ResponseEntity.ok(response);
    }

    // Listar todos os investimentos de um usuário (com rendimento atualizado)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvestmentResponseDTO>> listarPorUsuario(
            @PathVariable Long userId
    ) {
        List<InvestmentResponseDTO> lista = investmentService.listarPorUsuario(userId)
                .stream()
                .map(inv -> new InvestmentResponseDTO(
                        inv.getId(),
                        inv.getType(),
                        inv.getAmount(),
                        inv.getCurrentAmount(),
                        inv.getMonthlyRate(),
                        inv.getStatus(),
                        inv.getAccount().getAccountNumber(),
                        inv.getAccount().getUser().getId(),
                        inv.getCreatedAt(),
                        inv.getLastYieldDate()
                ))
                .toList();

        return ResponseEntity.ok(lista);
    }

    // Resgatar investimento (credita na conta e marca como REDEEMED)
    @PostMapping("/{investmentId}/redeem")
    public ResponseEntity<InvestmentResponseDTO> resgatar(
            @PathVariable Long investmentId
    ) {
        InvestmentModel inv = investmentService.resgatar(investmentId);

        InvestmentResponseDTO response = new InvestmentResponseDTO(
                inv.getId(),
                inv.getType(),
                inv.getAmount(),
                inv.getCurrentAmount(),
                inv.getMonthlyRate(),
                inv.getStatus(),
                inv.getAccount().getAccountNumber(),
                inv.getAccount().getUser().getId(),
                inv.getCreatedAt(),
                inv.getLastYieldDate()
        );

        return ResponseEntity.ok(response);
    }
}
