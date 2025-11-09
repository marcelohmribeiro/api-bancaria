package com.banco.api_java.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "transfer")
public class TransferModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_account", nullable = false)
    private AccountModel fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account", nullable = false)
    private AccountModel toAccount;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    @Column
    private String description;

    @OneToMany(mappedBy = "transfer", cascade = CascadeType.ALL)
    private List<LedgerEntryModel> ledgerEntries;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public AccountModel getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(AccountModel fromAccount) {
        this.fromAccount = fromAccount;
    }

    public AccountModel getToAccount() {
        return toAccount;
    }

    public void setToAccount(AccountModel toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<LedgerEntryModel> getLedgerEntries() {
        return ledgerEntries;
    }

    public void setLedgerEntries(List<LedgerEntryModel> ledgerEntries) {
        this.ledgerEntries = ledgerEntries;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
