package com.banco.api_java.repositories;

import com.banco.api_java.models.SupportTicketModel;
import com.banco.api_java.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportTicketRepository extends JpaRepository<SupportTicketModel, Long> {
    List<SupportTicketModel> findByUserOrderByCreatedAtDesc(UserModel user);
}
