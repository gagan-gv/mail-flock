package com.example.mailflock.controllers;

import com.example.mailflock.dto.ContactRequest;
import com.example.mailflock.dto.DemoRequest;
import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.services.ContactService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/contact")
public class ContactController {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<MessageResponse> contact(ContactRequest request) {
        return contactService.contact(request);
    }

    @PostMapping("/demo")
    public ResponseEntity<MessageResponse> bookDemo(DemoRequest request) {
        return contactService.bookDemo(request);
    }
}
