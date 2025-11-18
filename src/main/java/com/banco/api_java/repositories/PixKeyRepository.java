package com.banco.api_java.repositories;

import com.banco.api_java.models.AccountModel;
import com.banco.api_java.models.PixKeyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PixKeyRepository extends JpaRepository<PixKeyModel, Long> {
    Optional<PixKeyModel> findByValueKey(String valueKey);
    List<PixKeyModel> findByAccount(AccountModel account);
    List<PixKeyModel> findByAccountUserId(Long userId);
}
