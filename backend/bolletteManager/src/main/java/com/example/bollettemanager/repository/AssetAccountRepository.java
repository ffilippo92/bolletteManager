package com.example.bollettemanager.repository;

import com.example.bollettemanager.entity.AssetAccountEntity;
import com.example.bollettemanager.enums.AccountType;
import com.example.bollettemanager.enums.Currency;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetAccountRepository extends JpaRepository<AssetAccountEntity, Long> {

  List<AssetAccountEntity> findByActiveTrue();

  List<AssetAccountEntity> findByType(AccountType type);

  List<AssetAccountEntity> findByCurrency(Currency currency);

  List<AssetAccountEntity> findByUserId(Long userId);

  Optional<AssetAccountEntity> findByIdAndUserId(Long id, Long userId);
}
