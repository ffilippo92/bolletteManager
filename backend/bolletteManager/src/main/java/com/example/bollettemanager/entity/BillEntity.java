package com.example.bollettemanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "bills")
public class BillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String provider;
    private Integer billingMonth;
    private Integer billingYear;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private String invoiceNumber;
    private BigDecimal amount;
    private String status;
    private LocalDate dueDate;
    private LocalDate paymentDate;
}
