package com.example.bollettemanager.service;

import com.example.bollettemanager.dto.ChangePasswordDto;
import com.example.bollettemanager.dto.UpdateEmailDto;
import com.example.bollettemanager.dto.UserDto;
import com.example.bollettemanager.dto.UserLoginRequestDto;
import com.example.bollettemanager.dto.UserRegistrationDto;

public interface UserService {

  UserDto register(UserRegistrationDto registrationDto);

  UserDto login(UserLoginRequestDto loginRequestDto);

  UserDto getUserProfile(Long userId);

  void deleteUser(Long userId);

  void changePassword(Long userId, ChangePasswordDto changePasswordDto);

  UserDto updateEmail(Long userId, UpdateEmailDto updateEmailDto);
}
