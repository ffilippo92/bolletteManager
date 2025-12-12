package com.example.bollettemanager.security;

import com.example.bollettemanager.entity.UserEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

  private final Long id;
  private final String username;
  private final String email;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;
  private final boolean enabled;

  private UserDetails(
      Long id,
      String username,
      String email,
      String password,
      Collection<? extends GrantedAuthority> authorities,
      boolean enabled) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
    this.enabled = enabled;
  }

  public static UserDetails build(UserEntity user) {
    List<GrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

    return new UserDetails(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPasswordHash(),
        authorities,
        user.isEnabled());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
