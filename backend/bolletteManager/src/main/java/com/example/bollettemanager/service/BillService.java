package com.example.bollettemanager.service;

import com.example.bollettemanager.dto.BillAttachmentDTO;
import com.example.bollettemanager.dto.BillDetailDTO;
import com.example.bollettemanager.dto.BillRequestDTO;
import com.example.bollettemanager.dto.BillResponseDTO;
import com.example.bollettemanager.enums.BillStatus;
import com.example.bollettemanager.enums.BillType;
import java.util.List;

public interface BillService {

  BillResponseDTO createBill(BillRequestDTO request);

  BillResponseDTO getBillById(Long id);

  BillResponseDTO updateBill(Long id, BillRequestDTO request);

  void deleteBill(Long id);

  List<BillResponseDTO> searchBills(
      Integer year, Integer month, String provider, BillStatus status, BillType type);

  BillDetailDTO getBillDetail(Long billId);

  BillDetailDTO saveOrUpdateBillDetail(Long billId, BillDetailDTO detailDto);

  void deleteBillDetail(Long billId);

  BillAttachmentDTO getBillAttachment(Long billId);

  BillAttachmentDTO saveOrUpdateBillAttachment(Long billId, BillAttachmentDTO attachmentDto);

  void deleteBillAttachment(Long billId);
}
