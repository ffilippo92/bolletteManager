package com.example.bollettemanager.controller;

import com.example.bollettemanager.dto.AssetAccountDTO;
import com.example.bollettemanager.service.AssetAccountService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Nota Importante: Asset account operations are automatically scoped to the authenticated user;
 * nessun user identifier è passato dalle API, la proprietà quindi è gestita a livello di servizio!.
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AssetAccountController {

  private final AssetAccountService assetAccountService;

  @PostMapping
  public ResponseEntity<AssetAccountDTO> createAccount(@RequestBody AssetAccountDTO dto) {
    AssetAccountDTO createdAccount = assetAccountService.createAccount(dto);
    return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<AssetAccountDTO> updateAccount(
      @PathVariable Long id, @RequestBody AssetAccountDTO dto) {
    AssetAccountDTO updatedAccount = assetAccountService.updateAccount(id, dto);
    return ResponseEntity.ok(updatedAccount);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> disableAccount(@PathVariable Long id) {
    assetAccountService.disableAccount(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<AssetAccountDTO>> getAllAccounts() {
    List<AssetAccountDTO> accounts = assetAccountService.getAllAccounts();
    return ResponseEntity.ok(accounts);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AssetAccountDTO> getAccountById(@PathVariable Long id) {
    AssetAccountDTO account = assetAccountService.getAccountById(id);
    return ResponseEntity.ok(account);
  }
}
