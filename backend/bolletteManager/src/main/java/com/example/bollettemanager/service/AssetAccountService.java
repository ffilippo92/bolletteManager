package com.example.bollettemanager.service;

import com.example.bollettemanager.dto.AssetAccountDto;
import java.util.List;

public interface AssetAccountService {
  AssetAccountDto createAccount(AssetAccountDto dto);

  AssetAccountDto updateAccount(Long id, AssetAccountDto dto);

  void disableAccount(Long id);

  AssetAccountDto getAccountById(Long id);

  List<AssetAccountDto> getAllAccounts();
}
