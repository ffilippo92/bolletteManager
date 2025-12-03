package com.example.bollettemanager.dto;

import com.example.bollettemanager.enums.BillKind;
import com.example.bollettemanager.enums.BillStatus;
import com.example.bollettemanager.enums.BillType;
import com.example.bollettemanager.enums.ConsumptionUnit;
import com.example.bollettemanager.enums.PaymentMethod;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class BillRequestDTO {

    @NotNull
    private BillKind billKind;

    @NotNull
    private BillType type;

    @NotNull
    @Size(max = 100)
    private String provider;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer billingMonth;

    @NotNull
    private Integer billingYear;

    private LocalDate periodStart;

    private LocalDate periodEnd;

    @Size(max = 50)
    private String invoiceNumber;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private BillStatus status;

    @NotNull
    private LocalDate dueDate;

    private LocalDate paymentDate;

    @NotNull
    private PaymentMethod paymentMethod;

    private BigDecimal consumptionValue;

    private ConsumptionUnit consumptionUnit;
}