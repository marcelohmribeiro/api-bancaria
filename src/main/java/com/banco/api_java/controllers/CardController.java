package com.banco.api_java.controllers;

import com.banco.api_java.dtos.CardRequestDTO;
import com.banco.api_java.dtos.CardResponseDTO;
import com.banco.api_java.models.CardModel;
import com.banco.api_java.services.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CardResponseDTO> createCard(@RequestBody CardRequestDTO dto) {

        CardModel card = cardService.criarCartao(dto.accountNumber(), dto.cardType());

        CardResponseDTO response = new CardResponseDTO(
                card.getId(),
                card.getCardNumber(),
                card.getCardType(),
                card.getStatus(),
                card.getExpirationDate(),
                card.getAccount().getAccountNumber()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CardResponseDTO>> listarCartoesDoUsuario(@PathVariable Long userId) {

        List<CardModel> cards = cardService.listarCartoesDoUsuario(userId);

        List<CardResponseDTO> response = cards.stream().map(card ->
                new CardResponseDTO(
                    card.getId(),
                    card.getCardNumber(),
                    card.getCardType(),
                    card.getStatus(),
                    card.getExpirationDate(),
                    card.getAccount().getAccountNumber()
            )
        ).toList();

        return ResponseEntity.ok(response);
    }
}
