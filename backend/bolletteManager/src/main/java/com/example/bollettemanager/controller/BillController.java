package com.example.bollettemanager.controller;

import com.example.bollettemanager.dto.BillAttachmentDTO;
import com.example.bollettemanager.dto.BillDetailDTO;
import com.example.bollettemanager.dto.BillRequestDTO;
import com.example.bollettemanager.dto.BillResponseDTO;
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
  public BillResponseDTO createBill(@RequestBody @Valid BillRequestDTO request) {
    return billService.createBill(request);
  }

  @GetMapping("/{id}")
  public BillResponseDTO getBill(@PathVariable Long id) {
    return billService.getBillById(id);
  }

  @PutMapping("/{id}")
  public BillResponseDTO updateBill(
      @PathVariable Long id, @RequestBody @Valid BillRequestDTO request) {
    return billService.updateBill(id, request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
    billService.deleteBill(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public List<BillResponseDTO> searchBills(
      @RequestParam(required = false) Integer year,
      @RequestParam(required = false) Integer month,
      @RequestParam(required = false) String provider,
      @RequestParam(required = false) BillStatus status,
      @RequestParam(required = false) BillType type) {
    return billService.searchBills(year, month, provider, status, type);
  }

  @GetMapping("/{billId}/detail")
  public ResponseEntity<BillDetailDTO> getBillDetail(@PathVariable Long billId) {
    BillDetailDTO detail = billService.getBillDetail(billId);
    if (detail == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(detail);
  }

  @PutMapping("/{billId}/detail")
  public ResponseEntity<BillDetailDTO> saveOrUpdateBillDetail(
      @PathVariable Long billId, @RequestBody BillDetailDTO dto) {
    BillDetailDTO saved = billService.saveOrUpdateBillDetail(billId, dto);
    return ResponseEntity.ok(saved);
  }

  @DeleteMapping("/{billId}/detail")
  public ResponseEntity<Void> deleteBillDetail(@PathVariable Long billId) {
    billService.deleteBillDetail(billId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{billId}/attachment")
  public ResponseEntity<BillAttachmentDTO> getBillAttachment(@PathVariable Long billId) {
    BillAttachmentDTO attachment = billService.getBillAttachment(billId);
    if (attachment == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(attachment);
  }

  @PostMapping("/{billId}/attachment")
  public ResponseEntity<BillAttachmentDTO> saveOrUpdateBillAttachment(
      @PathVariable Long billId, @RequestBody BillAttachmentDTO dto) {
    BillAttachmentDTO existing = billService.getBillAttachment(billId);
    BillAttachmentDTO saved = billService.saveOrUpdateBillAttachment(billId, dto);
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
