package com.example.bollettemanager.dto;

import com.example.bollettemanager.enums.Role;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

  private Long id;
  private String username;
  private String email;
  private Role role;
  private boolean enabled;
  private LocalDateTime createdAt;
}
