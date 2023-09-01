package com.example.mailflock.controllers;

import com.example.mailflock.dto.ContactRequest;
import com.example.mailflock.dto.DemoRequest;
import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.services.ContactService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contact")
public class ContactController {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<MessageResponse> contact(@RequestBody ContactRequest request) {
        return contactService.contact(request);
    }

    @PostMapping("/demo")
    public ResponseEntity<MessageResponse> bookDemo(@RequestBody DemoRequest request) {
        return contactService.bookDemo(request);
    }
}
