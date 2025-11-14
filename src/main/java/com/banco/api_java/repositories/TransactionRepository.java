package com.banco.api_java.repositories;

import com.banco.api_java.models.AccountModel;
import com.banco.api_java.models.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {

    List<TransactionModel> findByFromAccountOrToAccount(AccountModel from, AccountModel to);
}
