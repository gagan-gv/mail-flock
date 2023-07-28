package com.example.mailexchange.controllers;

import com.example.mailexchange.dto.AuthenticationRequest;
import com.example.mailexchange.dto.AuthenticationResponse;
import com.example.mailexchange.dto.RegistrationRequest;
import com.example.mailexchange.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthConrtoller {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody @Valid RegistrationRequest request) {
        return authService.registerUser(request);
    }

    @PatchMapping("/verify")
    public ResponseEntity<AuthenticationResponse> verifyUser(@RequestParam(name = "email") @Valid String emailId,
                                                             @RequestParam(name = "otp") @Valid String otp) {
        return authService.verifyUser(emailId, otp);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody @Valid AuthenticationRequest request) {
        return authService.login(request);
    }
}
