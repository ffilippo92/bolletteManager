package com.example.bollettemanager.dto;

import com.example.bollettemanager.enums.BillKind;
import com.example.bollettemanager.enums.BillStatus;
import com.example.bollettemanager.enums.BillType;
import com.example.bollettemanager.enums.ConsumptionUnit;
import com.example.bollettemanager.enums.PaymentMethod;
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
public class BillResponseDto {

  private Long id;

  private BillKind billKind;
  private BillType type;
  private String provider;

  private Integer billingMonth;
  private Integer billingYear;

  private LocalDate periodStart;
  private LocalDate periodEnd;

  private String invoiceNumber;

  private BigDecimal amount;
  private BillStatus status;

  private LocalDate dueDate;
  private LocalDate paymentDate;

  private PaymentMethod paymentMethod;

  private BigDecimal consumptionValue;
  private ConsumptionUnit consumptionUnit;

  private boolean hasAttachment;
}
