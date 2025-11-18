package com.banco.api_java.services;

import com.banco.api_java.dtos.StatementResponseDTO;
import com.banco.api_java.models.AccountModel;
import com.banco.api_java.models.TransactionModel;
import com.banco.api_java.repositories.AccountRepository;
import com.banco.api_java.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatementService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    public StatementService(AccountRepository accountRepository,
                            TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    public List<StatementResponseDTO> gerarExtrato(String accountNumber) {

        AccountModel account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

        List<TransactionModel> transacoes =
                transactionRepository.findByFromAccountOrToAccountOrderByCreatedAtDesc(
                        account, account
                );

        return transacoes.stream()
                .map(t -> toDTO(t, account.getId()))
                .toList();
    }

    private StatementResponseDTO toDTO(TransactionModel t, Long accountId) {
        String lado = t.getToAccount().getId().equals(accountId)
                ? "ENTRADA"
                : "SAIDA";

        return new StatementResponseDTO(
                t.getId(),
                t.getType().name(),
                t.getAmount(),
                t.getDescription(),
                t.getCreatedAt(),
                lado
        );
    }
}
