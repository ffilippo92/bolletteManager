package com.example.bollettemanager.controller;

import com.example.bollettemanager.dto.ChangePasswordDto;
import com.example.bollettemanager.dto.UpdateEmailDto;
import com.example.bollettemanager.dto.UserDto;
import com.example.bollettemanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserProfile(@PathVariable Long id) {
    UserDto userDto = userService.getUserProfile(id);
    return ResponseEntity.ok(userDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/change-password")
  public ResponseEntity<Void> changePassword(
      @PathVariable Long id, @RequestBody ChangePasswordDto changePasswordDto) {
    userService.changePassword(id, changePasswordDto);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/email")
  public ResponseEntity<UserDto> updateEmail(
      @PathVariable Long id, @RequestBody UpdateEmailDto updateEmailDto) {
    UserDto updatedUser = userService.updateEmail(id, updateEmailDto);
    return ResponseEntity.ok(updatedUser);
  }
}
