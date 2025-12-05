package com.example.bollettemanager.service;

import com.example.bollettemanager.dto.AssetAccountDTO;
import java.util.List;

public interface AssetAccountService {
    AssetAccountDTO createAccount(AssetAccountDTO dto);

    AssetAccountDTO updateAccount(Long id, AssetAccountDTO dto);

    void disableAccount(Long id);

    AssetAccountDTO getAccountById(Long id);

    List<AssetAccountDTO> getAllAccounts();
}