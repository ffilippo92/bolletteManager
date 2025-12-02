package com.example.bollettemanager.repository;

import com.example.bollettemanager.entity.BillAttachmentEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillAttachmentRepository extends JpaRepository<BillAttachmentEntity, Long> {

    List<BillAttachmentEntity> findByBillId(Long billId);

    Optional<BillAttachmentEntity> findFirstByBillIdOrderByUploadDateDesc(Long billId);
}