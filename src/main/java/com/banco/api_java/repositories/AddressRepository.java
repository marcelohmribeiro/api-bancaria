package com.banco.api_java.repositories;

import com.banco.api_java.models.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressModel, Long> {
    List<AddressModel> findByUserId(Long userId);
}
