package com.example.bollettemanager.service;

import com.example.bollettemanager.dto.ChangePasswordDto;
import com.example.bollettemanager.dto.UpdateEmailDto;
import com.example.bollettemanager.dto.UserDto;
import com.example.bollettemanager.dto.UserLoginRequestDto;
import com.example.bollettemanager.dto.UserRegistrationDto;
import com.example.bollettemanager.entity.UserEntity;
import com.example.bollettemanager.enums.Role;
import com.example.bollettemanager.repository.UserRepository;
import com.example.bollettemanager.service.UserService;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(UserRegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new RuntimeException("Username already in use");
        }
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        UserEntity userEntity = UserEntity.builder()
                .username(registrationDto.getUsername())
                .email(registrationDto.getEmail())
                .passwordHash(passwordEncoder.encode(registrationDto.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();

        UserEntity savedEntity = userRepository.save(userEntity);
        return toDto(savedEntity);
    }

    @Override
    public UserDto login(UserLoginRequestDto loginRequestDto) {
        UserEntity user = userRepository
                .findByUsername(loginRequestDto.getUsernameOrEmail())
                .orElseGet(() -> userRepository
                        .findByEmail(loginRequestDto.getUsernameOrEmail())
                        .orElseThrow(() -> new RuntimeException("Invalid credentials")));

        if (!user.isEnabled()) {
            throw new RuntimeException("User is disabled");
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        return toDto(user);
    }

    @Override
    public UserDto getUserProfile(Long userId) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return toDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        userRepository.delete(user);
    }

    @Override
    public void changePassword(Long userId, ChangePasswordDto changePasswordDto) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPasswordHash(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDto updateEmail(Long userId, UpdateEmailDto updateEmailDto) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        String newEmail = updateEmailDto.getNewEmail();
        if (userRepository.existsByEmail(newEmail) && !Objects.equals(user.getEmail(), newEmail)) {
            throw new RuntimeException("Email already in use");
        }

        user.setEmail(newEmail);
        UserEntity updatedUser = userRepository.save(user);
        return toDto(updatedUser);
    }

    private UserDto toDto(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .role(entity.getRole())
                .enabled(entity.isEnabled())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}