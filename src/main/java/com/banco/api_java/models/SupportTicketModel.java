package com.banco.api_java.models;

import com.banco.api_java.enums.TicketStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "support_tickets")
public class SupportTicketModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(nullable = false, length = 150)
    private String category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.ABERTO;

    // 🔹 N:N – usuários que ATENDEM o chamado (analistas de suporte)
    @ManyToMany(mappedBy = "assignedTickets")
    private List<UserModel> assignees;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public List<UserModel> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<UserModel> assignees) {
        this.assignees = assignees;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
