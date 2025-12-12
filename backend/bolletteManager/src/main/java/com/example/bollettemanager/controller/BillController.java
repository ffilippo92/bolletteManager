package com.example.bollettemanager.controller;

import com.example.bollettemanager.dto.BillAttachmentDto;
import com.example.bollettemanager.dto.BillDetailDto;
import com.example.bollettemanager.dto.BillRequestDto;
import com.example.bollettemanager.dto.BillResponseDto;
import com.example.bollettemanager.enums.BillStatus;
import com.example.bollettemanager.enums.BillType;
import com.example.bollettemanager.service.BillService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Nota Importante: Bill operations are automatically scoped to the authenticated user; nessun user
 * identifier è passato dalle API, la proprietà quindi è gestita a livello di servizio!.
 */
@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

  private final BillService billService;

  @PostMapping
  public BillResponseDto createBill(@RequestBody @Valid BillRequestDto request) {
    return billService.createBill(request);
  }

  @GetMapping("/{id}")
  public BillResponseDto getBill(@PathVariable Long id) {
    return billService.getBillById(id);
  }

  @PutMapping("/{id}")
  public BillResponseDto updateBill(
      @PathVariable Long id, @RequestBody @Valid BillRequestDto request) {
    return billService.updateBill(id, request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
    billService.deleteBill(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public List<BillResponseDto> searchBills(
      @RequestParam(required = false) Integer year,
      @RequestParam(required = false) Integer month,
      @RequestParam(required = false) String provider,
      @RequestParam(required = false) BillStatus status,
      @RequestParam(required = false) BillType type) {
    return billService.searchBills(year, month, provider, status, type);
  }

  @GetMapping("/{billId}/detail")
  public ResponseEntity<BillDetailDto> getBillDetail(@PathVariable Long billId) {
    BillDetailDto detail = billService.getBillDetail(billId);
    if (detail == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(detail);
  }

  @PutMapping("/{billId}/detail")
  public ResponseEntity<BillDetailDto> saveOrUpdateBillDetail(
      @PathVariable Long billId, @RequestBody BillDetailDto dto) {
    BillDetailDto saved = billService.saveOrUpdateBillDetail(billId, dto);
    return ResponseEntity.ok(saved);
  }

  @DeleteMapping("/{billId}/detail")
  public ResponseEntity<Void> deleteBillDetail(@PathVariable Long billId) {
    billService.deleteBillDetail(billId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{billId}/attachment")
  public ResponseEntity<BillAttachmentDto> getBillAttachment(@PathVariable Long billId) {
    BillAttachmentDto attachment = billService.getBillAttachment(billId);
    if (attachment == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(attachment);
  }

  @PostMapping("/{billId}/attachment")
  public ResponseEntity<BillAttachmentDto> saveOrUpdateBillAttachment(
      @PathVariable Long billId, @RequestBody BillAttachmentDto dto) {
    BillAttachmentDto existing = billService.getBillAttachment(billId);
    BillAttachmentDto saved = billService.saveOrUpdateBillAttachment(billId, dto);
    if (existing == null) {
      return ResponseEntity.status(201).body(saved);
    }
    return ResponseEntity.ok(saved);
  }

  @DeleteMapping("/{billId}/attachment")
  public ResponseEntity<Void> deleteBillAttachment(@PathVariable Long billId) {
    billService.deleteBillAttachment(billId);
    return ResponseEntity.noContent().build();
  }
}
