package com.banco.api_java.models;

import com.banco.api_java.enums.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "accounts")
public class AccountModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ATIVO;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, foreignKey = @ForeignKey(name = "fk_accounts_user"))
    private UserModel user;

    @OneToMany(mappedBy = "fromAccount")
    private List<TransactionModel> transfersSent;

    @OneToMany(mappedBy = "toAccount")
    private List<TransactionModel> transfersReceived;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<TransactionModel> getTransfersSent() {
        return transfersSent;
    }

    public void setTransfersSent(List<TransactionModel> transfersSent) {
        this.transfersSent = transfersSent;
    }

    public List<TransactionModel> getTransfersReceived() {
        return transfersReceived;
    }

    public void setTransfersReceived(List<TransactionModel> transfersReceived) {
        this.transfersReceived = transfersReceived;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
