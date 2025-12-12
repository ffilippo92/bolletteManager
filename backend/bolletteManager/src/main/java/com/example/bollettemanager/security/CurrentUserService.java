package com.example.bollettemanager.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserService {

  public Long getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
      throw new IllegalStateException("No authenticated user found");
    }

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    return userDetails.getId();
  }
}
