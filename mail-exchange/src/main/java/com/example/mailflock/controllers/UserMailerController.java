package com.example.mailflock.controllers;

import com.example.mailflock.dto.MailRequest;
import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.services.UserMailingService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
@CrossOrigin
public class UserMailerController {
    private final UserMailingService mailingService;
    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendMail(@RequestBody @Valid MailRequest request) {
        return mailingService.sendMail(request);
    }
}
