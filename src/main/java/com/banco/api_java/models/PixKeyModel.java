package com.banco.api_java.models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pix_key")
public class PixKeyModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type; // CPF, CNPJ, EMaAIL, TELEFONE, ALEATORIA

    @Column(nullable = false, unique = true)
    private String valueKey;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountModel account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValueKey() {
        return valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }
}
