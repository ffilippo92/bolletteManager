package com.example.bollettemanager.repository;

import com.example.bollettemanager.entity.BillEntity;
import com.example.bollettemanager.enums.BillStatus;
import com.example.bollettemanager.enums.BillType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<BillEntity, Long> {
  List<BillEntity> findByBillingYear(Integer billingYear);

  List<BillEntity> findByBillingYearAndBillingMonth(Integer billingYear, Integer billingMonth);

  List<BillEntity> findByType(BillType type);

  List<BillEntity> findByStatus(BillStatus status);

  List<BillEntity> findByProviderIgnoreCase(String provider);

  List<BillEntity> findByUserId(Long userId);

  Optional<BillEntity> findByIdAndUserId(Long id, Long userId);
}
