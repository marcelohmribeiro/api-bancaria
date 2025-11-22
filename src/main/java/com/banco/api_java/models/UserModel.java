package com.banco.api_java.models;

import com.banco.api_java.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class UserModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false, length = 60) // hash BCrypt ~60 chars
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private AccountModel account;

    @OneToMany(mappedBy = "user")
    private List<AddressModel> address;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @ManyToMany
    @JoinTable(
            name = "support_ticket_assignees",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id")
    )
    private List<SupportTicketModel> assignedTickets;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<AddressModel> getAddress() {
        return address;
    }

    public void setAddress(List<AddressModel> address) {
        this.address = address;
    }

    public List<SupportTicketModel> getAssignedTickets() {
        return assignedTickets;
    }

    public void setAssignedTickets(List<SupportTicketModel> assignedTickets) {
        this.assignedTickets = assignedTickets;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
