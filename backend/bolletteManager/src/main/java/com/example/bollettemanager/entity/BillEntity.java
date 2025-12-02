package com.example.bollettemanager.entity;

import com.example.bollettemanager.enums.BillKind;
import com.example.bollettemanager.enums.BillStatus;
import com.example.bollettemanager.enums.BillType;
import com.example.bollettemanager.enums.ConsumptionUnit;
import com.example.bollettemanager.enums.PaymentMethod;
import com.example.bollettemanager.entity.BillAttachmentEntity;
import com.example.bollettemanager.entity.BillDetailEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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

    @Enumerated(EnumType.STRING)
    private BillKind billKind;

    @Enumerated(EnumType.STRING)
    private BillType type;

    private String provider;

    private Integer billingMonth;

    private Integer billingYear;

    private LocalDate periodStart;

    private LocalDate periodEnd;

    private String invoiceNumber;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private BillStatus status;

    private LocalDate dueDate;

    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private BigDecimal consumptionValue;

    @Enumerated(EnumType.STRING)
    private ConsumptionUnit consumptionUnit;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillAttachmentEntity> attachments;

    @OneToOne(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private BillDetailEntity detail;
}
