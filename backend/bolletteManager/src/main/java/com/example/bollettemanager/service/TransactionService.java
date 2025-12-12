package com.example.bollettemanager.service;

import com.example.bollettemanager.dto.TransactionDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

  TransactionDto createTransaction(TransactionDto dto);

  TransactionDto updateTransaction(Long id, TransactionDto dto);

  void deleteTransaction(Long id);

  TransactionDto getTransactionById(Long id);

  List<TransactionDto> getAllTransactions();

  List<TransactionDto> searchTransactions(
      Long assetAccountId,
      String category,
      LocalDate dateFrom,
      LocalDate dateTo,
      BigDecimal amountMin,
      BigDecimal amountMax);

  void transferBetweenAccounts(
      Long fromAccountId, Long toAccountId, BigDecimal amount, LocalDate date, String description);
}
