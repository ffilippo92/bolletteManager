package com.example.bollettemanager.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    private Long id;
    private LocalDate date;
    private String description;
    private String category;
    private BigDecimal amount;
    private Long assetAccountId;
    private String note;
}