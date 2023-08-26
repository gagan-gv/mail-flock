package com.example.mailflock.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.dto.SubscriptionContent;
import com.example.mailflock.services.SubscriptionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscribe")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<MessageResponse> subscribe(@RequestParam String emailId) {
        return subscriptionService.subscribe(emailId);
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> unsubscribe(@RequestParam String emailId) {
        return subscriptionService.unsubscribe(emailId);
    }

    @PostMapping("/send")
    public ResponseEntity<MessageResponse> shareWithSubscribers(@RequestBody SubscriptionContent content) {
        return subscriptionService.shareWithSubscribers(content);
    }
}
