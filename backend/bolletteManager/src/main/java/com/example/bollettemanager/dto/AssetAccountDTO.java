package com.example.bollettemanager.dto;

import com.example.bollettemanager.enums.AccountType;
import com.example.bollettemanager.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetAccountDTO {

  private Long id;
  private String name;
  private AccountType type;
  private Currency currency;
  private boolean active;
}
