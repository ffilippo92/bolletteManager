package com.example.bollettemanager.service;

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
            Integer year,
            Integer month,
            String provider,
            BillStatus status,
            BillType type
    );
}