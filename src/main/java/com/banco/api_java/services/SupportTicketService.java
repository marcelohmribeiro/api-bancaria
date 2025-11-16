package com.banco.api_java.services;

import com.banco.api_java.enums.TicketStatus;
import com.banco.api_java.models.SupportTicketModel;
import com.banco.api_java.models.UserModel;
import com.banco.api_java.repositories.SupportTicketRepository;
import com.banco.api_java.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportTicketService {
    private final SupportTicketRepository supportTicketRepository;
    private final UserRepository userRepository;
    public SupportTicketService(SupportTicketRepository supportTicketRepository,
                                UserRepository userRepository) {
        this.supportTicketRepository = supportTicketRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public SupportTicketModel criarTicket(Long userId,
                                          String description,
                                          String category) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        SupportTicketModel ticket = new SupportTicketModel();
        ticket.setUser(user);
        ticket.setDescription(description);
        ticket.setCategory(category);
        ticket.setStatus(TicketStatus.ABERTO);
        return supportTicketRepository.save(ticket);
    }

    public List<SupportTicketModel> listarTicketsPorUsuario(Long userId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        return supportTicketRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public SupportTicketModel buscarPorId(Long ticketId) {
        return supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket não encontrado."));
    }

    @Transactional
    public SupportTicketModel atualizarStatus(Long ticketId, TicketStatus novoStatus) {
        SupportTicketModel ticket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket não encontrado."));

        ticket.setStatus(novoStatus);
        return supportTicketRepository.save(ticket);
    }
}
