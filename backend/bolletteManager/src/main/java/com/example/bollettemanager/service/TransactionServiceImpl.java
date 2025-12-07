package com.example.bollettemanager.service;

import com.example.bollettemanager.dto.TransactionDTO;
import com.example.bollettemanager.entity.AssetAccountEntity;
import com.example.bollettemanager.entity.TransactionEntity;
import com.example.bollettemanager.entity.UserEntity;
import com.example.bollettemanager.repository.AssetAccountRepository;
import com.example.bollettemanager.repository.TransactionRepository;
import com.example.bollettemanager.repository.UserRepository;
import com.example.bollettemanager.security.CurrentUserService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AssetAccountRepository assetAccountRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;

    @Override
    public TransactionDTO createTransaction(TransactionDTO dto) {
        Long currentUserId = currentUserService.getCurrentUserId();
        UserEntity user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + currentUserId));
        AssetAccountEntity assetAccount = getAssetAccount(dto.getAssetAccountId(), currentUserId);
        TransactionEntity entity = toEntity(dto, assetAccount, user);
        TransactionEntity saved = transactionRepository.save(entity);
        return toDto(saved);
    }

    @Override
    public TransactionDTO updateTransaction(Long id, TransactionDTO dto) {
        Long currentUserId = currentUserService.getCurrentUserId();
        TransactionEntity existing = transactionRepository.findByIdAndUserId(id, currentUserId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));

        AssetAccountEntity assetAccount = getAssetAccount(dto.getAssetAccountId(), currentUserId);

        existing.setDate(dto.getDate());
        existing.setDescription(dto.getDescription());
        existing.setCategory(dto.getCategory());
        existing.setAmount(dto.getAmount());
        existing.setAssetAccount(assetAccount);
        existing.setNote(dto.getNote());

        TransactionEntity saved = transactionRepository.save(existing);
        return toDto(saved);
    }

    @Override
    public void deleteTransaction(Long id) {
        Long currentUserId = currentUserService.getCurrentUserId();
        transactionRepository.findByIdAndUserId(id, currentUserId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
        transactionRepository.deleteById(id);
    }

    @Override
    public TransactionDTO getTransactionById(Long id) {
        Long currentUserId = currentUserService.getCurrentUserId();
        TransactionEntity entity = transactionRepository.findByIdAndUserId(id, currentUserId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
        return toDto(entity);
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        Long currentUserId = currentUserService.getCurrentUserId();
        return transactionRepository.findByUserId(currentUserId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> searchTransactions(
            Long assetAccountId,
            String category,
            LocalDate dateFrom,
            LocalDate dateTo,
            BigDecimal amountMin,
            BigDecimal amountMax) {

        Long currentUserId = currentUserService.getCurrentUserId();

        return transactionRepository.findByUserId(currentUserId).stream()
                .filter(transaction -> filterByAssetAccount(transaction, assetAccountId))
                .filter(transaction -> filterByCategory(transaction, category))
                .filter(transaction -> filterByDateRange(transaction, dateFrom, dateTo))
                .filter(transaction -> filterByAmountRange(transaction, amountMin, amountMax))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void transferBetweenAccounts(
            Long fromAccountId,
            Long toAccountId,
            BigDecimal amount,
            LocalDate date,
            String description) {

        Long currentUserId = currentUserService.getCurrentUserId();
        UserEntity user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + currentUserId));
        AssetAccountEntity fromAccount = getAssetAccount(fromAccountId, currentUserId);
        AssetAccountEntity toAccount = getAssetAccount(toAccountId, currentUserId);

        BigDecimal absoluteAmount = amount.abs();
        BigDecimal outgoingAmount = absoluteAmount.negate();
        BigDecimal incomingAmount = absoluteAmount;

        String baseDescription = (description != null && !description.isBlank())
                ? description
                : "Transfer between accounts";
        String outgoingDescription = baseDescription + " - to account " + toAccountId;
        String incomingDescription = baseDescription + " - from account " + fromAccountId;

        TransactionEntity outgoingTransaction = TransactionEntity.builder()
                .date(date)
                .description(outgoingDescription)
                .amount(outgoingAmount)
                .assetAccount(fromAccount)
                .user(user)
                .build();

        TransactionEntity incomingTransaction = TransactionEntity.builder()
                .date(date)
                .description(incomingDescription)
                .amount(incomingAmount)
                .assetAccount(toAccount)
                .user(user)
                .build();

        transactionRepository.save(outgoingTransaction);
        transactionRepository.save(incomingTransaction);
    }

    private boolean filterByAssetAccount(TransactionEntity transaction, Long assetAccountId) {
        if (assetAccountId == null) {
            return true;
        }
        return transaction.getAssetAccount() != null
                && assetAccountId.equals(transaction.getAssetAccount().getId());
    }

    private boolean filterByCategory(TransactionEntity transaction, String category) {
        if (category == null || category.isBlank()) {
            return true;
        }
        return transaction.getCategory() != null && category.equalsIgnoreCase(transaction.getCategory());
    }

    private boolean filterByDateRange(TransactionEntity transaction, LocalDate dateFrom, LocalDate dateTo) {
        LocalDate transactionDate = transaction.getDate();
        if (dateFrom != null && (transactionDate == null || transactionDate.isBefore(dateFrom))) {
            return false;
        }
        if (dateTo != null && (transactionDate == null || transactionDate.isAfter(dateTo))) {
            return false;
        }
        return true;
    }

    private boolean filterByAmountRange(TransactionEntity transaction, BigDecimal amountMin, BigDecimal amountMax) {
        BigDecimal transactionAmount = transaction.getAmount();
        if (amountMin != null && (transactionAmount == null || transactionAmount.compareTo(amountMin) < 0)) {
            return false;
        }
        if (amountMax != null && (transactionAmount == null || transactionAmount.compareTo(amountMax) > 0)) {
            return false;
        }
        return true;
    }

    private TransactionDTO toDto(TransactionEntity entity) {
        Long assetAccountId = entity.getAssetAccount() != null ? entity.getAssetAccount().getId() : null;

        return TransactionDTO.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .amount(entity.getAmount())
                .assetAccountId(assetAccountId)
                .note(entity.getNote())
                .build();
    }

    private TransactionEntity toEntity(TransactionDTO dto, AssetAccountEntity assetAccount,UserEntity user) {
        return TransactionEntity.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .amount(dto.getAmount())
                .assetAccount(assetAccount)
                .note(dto.getNote())
                .build();
    }

    private AssetAccountEntity getAssetAccount(Long id,Long currentUserId) {
        if (id == null) {
            throw new RuntimeException("Asset account id cannot be null");
        }
        return assetAccountRepository.findByIdAndUserId(id,currentUserId)
                .orElseThrow(() -> new RuntimeException("Asset account not found with id: " + id));
    }
}