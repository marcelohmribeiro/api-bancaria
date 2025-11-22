package com.banco.api_java.controllers;

import com.banco.api_java.dtos.SupportTicketResponseDTO;
import com.banco.api_java.dtos.UpdateTicketStatusDTO;
import com.banco.api_java.models.SupportTicketModel;
import com.banco.api_java.services.SupportTicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class SuportTicketController {
    private final SupportTicketService supportTicketService;
    public SuportTicketController(SupportTicketService supportTicketService) {
        this.supportTicketService = supportTicketService;
    }

    @PostMapping
    public ResponseEntity<SupportTicketResponseDTO> criarTicket(
            @RequestBody SupportTicketResponseDTO dto) {

        SupportTicketModel ticket = supportTicketService.criarTicket(
                dto.userId(),
                dto.description(),
                dto.category()
        );

        SupportTicketResponseDTO response = new SupportTicketResponseDTO(
                ticket.getId(),
                ticket.getUser().getId(),
                ticket.getDescription(),
                ticket.getCategory(),
                ticket.getStatus(),
                ticket.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SupportTicketResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody UpdateTicketStatusDTO dto) {

        SupportTicketModel updated = supportTicketService.atualizarStatus(id, dto.status());

        SupportTicketResponseDTO response = new SupportTicketResponseDTO(
                updated.getId(),
                updated.getUser().getId(),
                updated.getDescription(),
                updated.getCategory(),
                updated.getStatus(),
                updated.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{ticketId}/assign/{adminId}")
    public ResponseEntity<SupportTicketResponseDTO> atribuirAdminAoTicket(
            @PathVariable Long ticketId,
            @PathVariable Long adminId
    ) {
        SupportTicketModel ticket =
                supportTicketService.atribuirSuporteAoTicket(ticketId, adminId);

        SupportTicketResponseDTO response = new SupportTicketResponseDTO(
                ticket.getId(),
                ticket.getUser().getId(),
                ticket.getDescription(),
                ticket.getCategory(),
                ticket.getStatus(),
                ticket.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }
}
