package com.example.bollettemanager.service;

import com.example.bollettemanager.dto.BillRequestDTO;
import com.example.bollettemanager.dto.BillResponseDTO;
import com.example.bollettemanager.entity.BillEntity;
import com.example.bollettemanager.enums.BillStatus;
import com.example.bollettemanager.enums.BillType;
import com.example.bollettemanager.repository.BillAttachmentRepository;
import com.example.bollettemanager.repository.BillRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final BillAttachmentRepository billAttachmentRepository;

    @Override
    public BillResponseDTO createBill(BillRequestDTO request) {
        BillEntity entity = toEntity(request);
        BillEntity saved = billRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    public BillResponseDTO getBillById(Long id) {
        BillEntity entity = billRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found"));
        return toResponse(entity);
    }

    @Override
    public BillResponseDTO updateBill(Long id, BillRequestDTO request) {
        BillEntity entity = billRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found"));

        entity.setBillKind(request.getBillKind());
        entity.setType(request.getType());
        entity.setProvider(request.getProvider());
        entity.setBillingMonth(request.getBillingMonth());
        entity.setBillingYear(request.getBillingYear());
        entity.setPeriodStart(request.getPeriodStart());
        entity.setPeriodEnd(request.getPeriodEnd());
        entity.setInvoiceNumber(request.getInvoiceNumber());
        entity.setAmount(request.getAmount());
        entity.setStatus(request.getStatus());
        entity.setDueDate(request.getDueDate());
        entity.setPaymentDate(request.getPaymentDate());
        entity.setPaymentMethod(request.getPaymentMethod());
        entity.setConsumptionValue(request.getConsumptionValue());
        entity.setConsumptionUnit(request.getConsumptionUnit());

        BillEntity updated = billRepository.save(entity);
        return toResponse(updated);
    }

    @Override
    public void deleteBill(Long id) {
        BillEntity entity = billRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found"));
        billRepository.delete(entity);
    }

    @Override
    public List<BillResponseDTO> searchBills(
            Integer year,
            Integer month,
            String provider,
            BillStatus status,
            BillType type
    ) {
        List<BillEntity> entities;
        if (year != null && month != null) {
            entities = billRepository.findByBillingYearAndBillingMonth(year, month);
        } else if (year != null) {
            entities = billRepository.findByBillingYear(year);
        } else if (provider != null) {
            entities = billRepository.findByProviderIgnoreCase(provider);
        } else if (status != null) {
            entities = billRepository.findByStatus(status);
        } else if (type != null) {
            entities = billRepository.findByType(type);
        } else {
            entities = billRepository.findAll();
        }

        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private BillEntity toEntity(BillRequestDTO dto) {
        return BillEntity.builder()
                .billKind(dto.getBillKind())
                .type(dto.getType())
                .provider(dto.getProvider())
                .billingMonth(dto.getBillingMonth())
                .billingYear(dto.getBillingYear())
                .periodStart(dto.getPeriodStart())
                .periodEnd(dto.getPeriodEnd())
                .invoiceNumber(dto.getInvoiceNumber())
                .amount(dto.getAmount())
                .status(dto.getStatus())
                .dueDate(dto.getDueDate())
                .paymentDate(dto.getPaymentDate())
                .paymentMethod(dto.getPaymentMethod())
                .consumptionValue(dto.getConsumptionValue())
                .consumptionUnit(dto.getConsumptionUnit())
                .build();
    }

    private BillResponseDTO toResponse(BillEntity entity) {
        return BillResponseDTO.builder()
                .id(entity.getId())
                .billKind(entity.getBillKind())
                .type(entity.getType())
                .provider(entity.getProvider())
                .billingMonth(entity.getBillingMonth())
                .billingYear(entity.getBillingYear())
                .periodStart(entity.getPeriodStart())
                .periodEnd(entity.getPeriodEnd())
                .invoiceNumber(entity.getInvoiceNumber())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .dueDate(entity.getDueDate())
                .paymentDate(entity.getPaymentDate())
                .paymentMethod(entity.getPaymentMethod())
                .consumptionValue(entity.getConsumptionValue())
                .consumptionUnit(entity.getConsumptionUnit())
                .hasAttachment(entity.getAttachments() != null && !entity.getAttachments().isEmpty())
                .build();
    }
}