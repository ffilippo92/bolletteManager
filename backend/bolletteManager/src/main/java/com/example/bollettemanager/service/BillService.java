package com.example.bollettemanager.service;

import com.example.bollettemanager.dto.BillAttachmentDto;
import com.example.bollettemanager.dto.BillDetailDto;
import com.example.bollettemanager.dto.BillRequestDto;
import com.example.bollettemanager.dto.BillResponseDto;
import com.example.bollettemanager.enums.BillStatus;
import com.example.bollettemanager.enums.BillType;
import java.util.List;

public interface BillService {

  BillResponseDto createBill(BillRequestDto request);

  BillResponseDto getBillById(Long id);

  BillResponseDto updateBill(Long id, BillRequestDto request);

  void deleteBill(Long id);

  List<BillResponseDto> searchBills(
      Integer year, Integer month, String provider, BillStatus status, BillType type);

  BillDetailDto getBillDetail(Long billId);

  BillDetailDto saveOrUpdateBillDetail(Long billId, BillDetailDto detailDto);

  void deleteBillDetail(Long billId);

  BillAttachmentDto getBillAttachment(Long billId);

  BillAttachmentDto saveOrUpdateBillAttachment(Long billId, BillAttachmentDto attachmentDto);

  void deleteBillAttachment(Long billId);
}
