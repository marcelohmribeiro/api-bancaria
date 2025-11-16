package com.banco.api_java.services;

import com.banco.api_java.dtos.ExtratoResponseDTO;
import com.banco.api_java.models.AccountModel;
import com.banco.api_java.models.TransactionModel;
import com.banco.api_java.repositories.AccountRepository;
import com.banco.api_java.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtratoService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    public ExtratoService(AccountRepository accountRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    public List<ExtratoResponseDTO> gerarExtrato(String accountNumber) {

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

    private ExtratoResponseDTO toDTO(TransactionModel t, Long accountId) {
        String lado = t.getToAccount().getId().equals(accountId)
                ? "ENTRADA"
                : "SAIDA";

        return new ExtratoResponseDTO(
                t.getId(),
                t.getType().name(),
                t.getAmount(),
                t.getDescription(),
                t.getCreatedAt(),
                lado
        );
    }
}
