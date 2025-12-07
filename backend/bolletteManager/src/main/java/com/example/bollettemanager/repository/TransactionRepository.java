package com.example.bollettemanager.repository;

import com.example.bollettemanager.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findByAssetAccountId(Long assetAccountId);

    List<TransactionEntity> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<TransactionEntity> findByAssetAccountIdAndDateBetween(Long assetAccountId, LocalDate startDate, LocalDate endDate);

    List<TransactionEntity> findByCategoryIgnoreCase(String category);

    List<TransactionEntity> findByUserId(Long userId);

    Optional<TransactionEntity> findByIdAndUserId(Long id, Long userId);
}