package com.example.bollettemanager.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillAttachmentDto {
  private Long id;
  private Long billId;
  private String fileName;
  private String storagePath;
  private String contentType;
  private LocalDateTime uploadDate;
}
