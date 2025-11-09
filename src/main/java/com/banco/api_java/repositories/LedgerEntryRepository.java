package com.banco.api_java.repositories;

import com.banco.api_java.models.LedgerEntryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerEntryRepository extends JpaRepository<LedgerEntryModel, Long> {
}
