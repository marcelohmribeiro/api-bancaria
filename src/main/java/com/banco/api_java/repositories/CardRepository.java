package com.banco.api_java.repositories;

import com.banco.api_java.models.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardModel, Long> {
    Optional<CardModel> findByCardNumber(String cardNumber);
}
