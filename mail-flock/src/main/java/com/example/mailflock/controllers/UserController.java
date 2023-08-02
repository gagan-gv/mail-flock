package com.example.mailflock.controllers;

import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.dto.UserUpdateRequest;
import com.example.mailflock.services.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final UserService userService;
    @PutMapping
    public ResponseEntity<MessageResponse> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        return userService.updateUser(request);
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteUser() {
        return userService.deleteUser();
    }
}
