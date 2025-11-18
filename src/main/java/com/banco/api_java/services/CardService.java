package com.banco.api_java.services;

import com.banco.api_java.enums.Side;
import com.banco.api_java.enums.Status;
import com.banco.api_java.models.AccountModel;
import com.banco.api_java.models.CardModel;
import com.banco.api_java.repositories.AccountRepository;
import com.banco.api_java.repositories.CardRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final SecureRandom random = new SecureRandom();
    public CardService(CardRepository cardRepository, AccountRepository accountRepository) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    public List<CardModel> listarCartoesDoUsuario(Long userId) {
        return cardRepository.findByAccountUserId(userId);
    }

    public CardModel criarCartao(String accountNumber, String cardType) {

        AccountModel account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

        // Gerar número do cartão
        String cardNumber = generateUniqueCardNumber();

        // Gerar CVV
        String cvv = String.format("%03d", random.nextInt(1000));

        // Gerar Data de Expiração (+5 anos)
        LocalDate expirationDate = LocalDate.now().plusYears(5);

        CardModel card = new CardModel();
        card.setAccount(account);
        card.setCardNumber(cardNumber);
        card.setCardType(Side.valueOf(cardType.toUpperCase()));
        card.setCvv(cvv);
        card.setExpirationDate(expirationDate);
        card.setStatus(Status.ATIVO);

        return cardRepository.save(card);
    }

    private String generateUniqueCardNumber() {
        String number;
        do {
            number = generateRandomCardNumber();
        } while (cardRepository.findByCardNumber(number).isPresent());
        return number;
    }

    private String generateRandomCardNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
