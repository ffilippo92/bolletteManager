package com.example.bollettemanager.service;

import com.example.bollettemanager.dto.BillAttachmentDTO;
import com.example.bollettemanager.dto.BillDetailDTO;
import com.example.bollettemanager.dto.BillRequestDTO;
import com.example.bollettemanager.dto.BillResponseDTO;
import com.example.bollettemanager.entity.BillAttachmentEntity;
import com.example.bollettemanager.entity.BillDetailEntity;
import com.example.bollettemanager.entity.BillEntity;
import com.example.bollettemanager.entity.UserEntity;
import com.example.bollettemanager.enums.BillStatus;
import com.example.bollettemanager.enums.BillType;
import com.example.bollettemanager.repository.BillAttachmentRepository;
import com.example.bollettemanager.repository.BillDetailRepository;
import com.example.bollettemanager.repository.BillRepository;
import com.example.bollettemanager.repository.UserRepository;
import com.example.bollettemanager.security.CurrentUserService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final BillAttachmentRepository billAttachmentRepository;
    private final BillDetailRepository billDetailRepository;
    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;

    @Override
    public BillResponseDTO createBill(BillRequestDTO request) {
        Long currentUserId = currentUserService.getCurrentUserId();
        UserEntity user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalStateException("Current user not found"));
        BillEntity entity = toEntity(request);
        entity.setUser(user);
        BillEntity saved = billRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    public BillResponseDTO getBillById(Long id) {
        BillEntity entity = getBillForCurrentUser(id);
        return toResponse(entity);
    }

    @Override
    public BillResponseDTO updateBill(Long id, BillRequestDTO request) {
        BillEntity entity = getBillForCurrentUser(id);

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
        BillEntity entity = getBillForCurrentUser(id);
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
        Long currentUserId = currentUserService.getCurrentUserId();
        entities = billRepository.findByUserId(currentUserId).stream()
                .filter(entity -> year == null || Objects.equals(entity.getBillingYear(), year))
                .filter(entity -> month == null || Objects.equals(entity.getBillingMonth(), month))
                .filter(entity -> provider == null || (entity.getProvider() != null
                        && entity.getProvider().equalsIgnoreCase(provider)))
                .filter(entity -> status == null || status.equals(entity.getStatus()))
                .filter(entity -> type == null || type.equals(entity.getType()))
                .collect(Collectors.toList());

        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BillDetailDTO getBillDetail(Long billId) {
        BillEntity bill = getBillForCurrentUser(billId);
        Optional<BillDetailEntity> detail = billDetailRepository.findByBillId(bill.getId());
        return detail.map(this::toDetailDto).orElse(null);
    }

    @Override
    public BillDetailDTO saveOrUpdateBillDetail(Long billId, BillDetailDTO detailDto) {
        BillEntity bill = getBillForCurrentUser(billId);
        BillDetailEntity detail = billDetailRepository.findByBillId(bill.getId())
                .orElseGet(BillDetailEntity::new);

        detail.setBill(bill);
        detail.setValue1Label(detailDto.getValue1Label());
        detail.setValue1Amount(detailDto.getValue1Amount());
        detail.setNotes(detailDto.getNotes());

        BillDetailEntity saved = billDetailRepository.save(detail);
        bill.setDetail(saved);
        return toDetailDto(saved);
    }

    @Override
    public void deleteBillDetail(Long billId) {
        BillEntity bill = getBillForCurrentUser(billId);
        billDetailRepository.findByBillId(bill.getId())
                .ifPresent(billDetailRepository::delete);
    }

    @Override
    public BillAttachmentDTO getBillAttachment(Long billId) {
        BillEntity bill = getBillForCurrentUser(billId);
        List<BillAttachmentEntity> attachments = billAttachmentRepository.findByBillId(bill.getId());
        if (attachments == null || attachments.isEmpty()) {
            return null;
        }
        return toAttachmentDto(attachments.get(0));
    }

    @Override
    public BillAttachmentDTO saveOrUpdateBillAttachment(Long billId, BillAttachmentDTO attachmentDto) {
        BillEntity bill = getBillForCurrentUser(billId);
        List<BillAttachmentEntity> attachments = billAttachmentRepository.findByBillId(bill.getId());

        BillAttachmentEntity attachment;
        if (attachments == null || attachments.isEmpty()) {
            attachment = new BillAttachmentEntity();
            attachment.setBill(bill);
        } else {
            attachment = attachments.get(0);
        }

        attachment.setFileName(attachmentDto.getFileName());
        attachment.setStoragePath(attachmentDto.getStoragePath());
        attachment.setContentType(attachmentDto.getContentType());
        attachment.setUploadDate(attachmentDto.getUploadDate());

        BillAttachmentEntity saved = billAttachmentRepository.save(attachment);
        return toAttachmentDto(saved);
    }

    @Override
    public void deleteBillAttachment(Long billId) {
        BillEntity bill = getBillForCurrentUser(billId);
        List<BillAttachmentEntity> attachments = billAttachmentRepository.findByBillId(bill.getId());
        if (attachments != null && !attachments.isEmpty()) {
            billAttachmentRepository.delete(attachments.get(0));
        }
    }

    private BillEntity getBillForCurrentUser(Long billId) {
        Long currentUserId = currentUserService.getCurrentUserId();
        return billRepository.findByIdAndUserId(billId, currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found"));
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

    private BillDetailDTO toDetailDto(BillDetailEntity entity) {
        return BillDetailDTO.builder()
                .id(entity.getId())
                .billId(entity.getBill() != null ? entity.getBill().getId() : null)
                .value1Label(entity.getValue1Label())
                .value1Amount(entity.getValue1Amount())
                .notes(entity.getNotes())
                .build();
    }

    private BillAttachmentDTO toAttachmentDto(BillAttachmentEntity entity) {
        return BillAttachmentDTO.builder()
                .id(entity.getId())
                .billId(entity.getBill() != null ? entity.getBill().getId() : null)
                .fileName(entity.getFileName())
                .storagePath(entity.getStoragePath())
                .contentType(entity.getContentType())
                .uploadDate(entity.getUploadDate())
                .build();
    }
}