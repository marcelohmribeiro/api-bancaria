package com.banco.api_java.controllers;

import com.banco.api_java.dtos.CardRequestDTO;
import com.banco.api_java.dtos.CardResponseDTO;
import com.banco.api_java.models.CardModel;
import com.banco.api_java.services.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        String fullNumber = card.getCardNumber();
        String ultimos4 = fullNumber.substring(fullNumber.length() - 4);
        String numeroMascarado = "**** **** **** " + ultimos4;

        CardResponseDTO response = new CardResponseDTO(
                card.getId(),
                numeroMascarado,
                ultimos4,
                card.getCardType(),
                card.getStatus(),
                card.getExpirationDate(),
                card.getAccount().getAccountNumber()
        );

        return ResponseEntity.ok(response);
    }
}
