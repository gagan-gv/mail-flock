package com.example.mailflock.controllers;

import com.example.mailflock.dto.AuthenticationRequest;
import com.example.mailflock.dto.AuthenticationResponse;
import com.example.mailflock.dto.RegistrationRequest;
import com.example.mailflock.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthConrtoller {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(
        @RequestBody @Valid @NonNull RegistrationRequest request
    ) {
        return authService.registerUser(request);
    }

    @PatchMapping("/verify")
    public ResponseEntity<AuthenticationResponse> verifyUser(
        @RequestParam(name = "email") @Valid @NonNull String emailId,
        @RequestParam(name = "otp") @Valid @NonNull String otp
    ) {
        return authService.verifyUser(emailId, otp);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(
        @RequestBody @Valid @NonNull AuthenticationRequest request
    ) {
        return authService.login(request);
    }

    @PostMapping("/renew")
    public ResponseEntity<AuthenticationResponse> refreshToken(@NonNull HttpServletRequest request) {
        return authService.refreshToken(request);
    }
}
