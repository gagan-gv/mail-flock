package com.example.mailflock.controllers;

import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.dto.SubscriptionContent;
import com.example.mailflock.services.SubscriptionService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
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
