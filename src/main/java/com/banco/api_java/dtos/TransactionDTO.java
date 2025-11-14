package com.banco.api_java.dtos;

import com.banco.api_java.enums.TransactionType;
import com.banco.api_java.models.TransactionModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(
        Long id,
        TransactionType type,
        BigDecimal amount,
        String description,
        String fromAccountNumber,
        String fromUserName,
        String toAccountNumber,
        String toUserName,
        LocalDateTime createdAt
) {

    public TransactionDTO(TransactionModel tx) {
        this(
                tx.getId(),
                tx.getType(),
                tx.getAmount(),
                tx.getDescription(),
                // from
                tx.getFromAccount() != null ? tx.getFromAccount().getAccountNumber() : null,
                tx.getFromAccount() != null ? tx.getFromAccount().getUser().getName() : null,
                // to
                tx.getToAccount() != null ? tx.getToAccount().getAccountNumber() : null,
                tx.getToAccount() != null ? tx.getToAccount().getUser().getName() : null,
                tx.getCreatedAt()
        );
    }
}
