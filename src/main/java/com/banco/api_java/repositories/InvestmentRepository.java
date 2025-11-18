package com.banco.api_java.repositories;

import com.banco.api_java.models.AccountModel;
import com.banco.api_java.models.InvestmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<InvestmentModel, Long> {
    List<InvestmentModel> findByAccountUserId(Long userId);
}
