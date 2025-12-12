package com.example.bollettemanager.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillDetailDto {

  private Long id;

  private Long billId;

  private String value1Label;

  private BigDecimal value1Amount;

  private String notes;
}
