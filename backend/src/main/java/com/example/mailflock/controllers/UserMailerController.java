package com.example.mailflock.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mailflock.dto.MailRequest;
import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.services.UserMailingService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class UserMailerController {
    private final UserMailingService mailingService;
    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendMail(@RequestBody @Valid MailRequest request) {
        return mailingService.sendMail(request);
    }
}
