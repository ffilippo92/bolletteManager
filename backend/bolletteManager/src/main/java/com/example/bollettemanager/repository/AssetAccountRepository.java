package com.example.bollettemanager.repository;

import com.example.bollettemanager.entity.AssetAccountEntity;
import com.example.bollettemanager.enums.AccountType;
import com.example.bollettemanager.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetAccountRepository extends JpaRepository<AssetAccountEntity, Long> {

    List<AssetAccountEntity> findByActiveTrue();

    List<AssetAccountEntity> findByType(AccountType type);

    List<AssetAccountEntity> findByCurrency(Currency currency);
}