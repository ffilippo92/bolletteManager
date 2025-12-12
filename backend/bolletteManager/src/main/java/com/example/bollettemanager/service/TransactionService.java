package com.example.bollettemanager.service;

import com.example.bollettemanager.dto.TransactionDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

  TransactionDTO createTransaction(TransactionDTO dto);

  TransactionDTO updateTransaction(Long id, TransactionDTO dto);

  void deleteTransaction(Long id);

  TransactionDTO getTransactionById(Long id);

  List<TransactionDTO> getAllTransactions();

  List<TransactionDTO> searchTransactions(
      Long assetAccountId,
      String category,
      LocalDate dateFrom,
      LocalDate dateTo,
      BigDecimal amountMin,
      BigDecimal amountMax);

  void transferBetweenAccounts(
      Long fromAccountId, Long toAccountId, BigDecimal amount, LocalDate date, String description);
}
