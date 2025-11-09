package com.banco.api_java.models;

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

    @Column(nullable = false)
    private String status = "active";

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, foreignKey = @ForeignKey(name = "fk_accounts_user"))
    private UserModel user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<LedgerEntryModel> ledgerEntries;

    @OneToMany(mappedBy = "fromAccount")
    private List<TransferModel> transfersSent;

    @OneToMany(mappedBy = "toAccount")
    private List<TransferModel> transfersReceived;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<LedgerEntryModel> getLedgerEntries() {
        return ledgerEntries;
    }

    public void setLedgerEntries(List<LedgerEntryModel> ledgerEntries) {
        this.ledgerEntries = ledgerEntries;
    }

    public List<TransferModel> getTransfersSent() {
        return transfersSent;
    }

    public void setTransfersSent(List<TransferModel> transfersSent) {
        this.transfersSent = transfersSent;
    }

    public List<TransferModel> getTransfersReceived() {
        return transfersReceived;
    }

    public void setTransfersReceived(List<TransferModel> transfersReceived) {
        this.transfersReceived = transfersReceived;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
