package com.example.bollettemanager.controller;

import com.example.bollettemanager.dto.AuthResponseDto;
import com.example.bollettemanager.dto.UserDto;
import com.example.bollettemanager.dto.UserLoginRequestDto;
import com.example.bollettemanager.dto.UserRegistrationDto;
import com.example.bollettemanager.security.JwtService;
import com.example.bollettemanager.security.UserDetailsServiceImpl;
import com.example.bollettemanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody UserRegistrationDto registrationDto) {
        UserDto userDto = userService.register(registrationDto);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        String token = jwtService.generateToken(userDetails);

        AuthResponseDto response = AuthResponseDto.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .user(userDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserLoginRequestDto loginRequestDto) {
        UserDto userDto = userService.login(loginRequestDto);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        String token = jwtService.generateToken(userDetails);

        AuthResponseDto response = AuthResponseDto.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .user(userDto)
                .build();

        return ResponseEntity.ok(response);
    }
}