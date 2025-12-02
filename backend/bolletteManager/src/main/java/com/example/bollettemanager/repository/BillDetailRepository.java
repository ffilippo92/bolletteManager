package com.example.bollettemanager.repository;

import com.example.bollettemanager.entity.BillDetailEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetailRepository extends JpaRepository<BillDetailEntity, Long> {
    Optional<BillDetailEntity> findByBillId(Long billId);
}