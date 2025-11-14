package com.banco.api_java.services;

import com.banco.api_java.enums.TransactionType;
import com.banco.api_java.models.AccountModel;
import com.banco.api_java.models.TransactionModel;
import com.banco.api_java.repositories.AccountRepository;
import com.banco.api_java.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository,
                           TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransactionModel transfer(String fromAccountNumber,
                                     String toAccountNumber,
                                     BigDecimal amount,
                                     String description) {

        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Conta de origem e destino não podem ser iguais.");
        }

        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Valor da transferência deve ser maior que zero.");
        }

        // Buscar contas
        AccountModel from = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Conta de origem não encontrada."));

        AccountModel to = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada."));

        if (!"active".equalsIgnoreCase(from.getStatus())) {
            throw new IllegalStateException("Conta de origem não está ativa.");
        }

        if (!"active".equalsIgnoreCase(to.getStatus())) {
            throw new IllegalStateException("Conta de destino não está ativa.");
        }

        // Checar saldo
        if (from.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente na conta de origem.");
        }

        // Debita origem e credita destino
        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        accountRepository.save(from);
        accountRepository.save(to);

        // Registra a transação
        TransactionModel tx = new TransactionModel();
        tx.setType(TransactionType.PIX);
        tx.setAmount(amount);
        tx.setDescription(description);
        tx.setFromAccount(from);
        tx.setToAccount(to);
        return transactionRepository.save(tx);
    }
}
