package com.example.mailflock.services;

import com.example.mailflock.dto.MailRequest;
import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.models.User;
import com.example.mailflock.services.interfaces.IUserMailingService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMailingService implements IUserMailingService {

    private final MailingManager mailingManager;

    @Override
    public ResponseEntity<MessageResponse> sendMail(MailRequest request) {
        MessageResponse response;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            for(String toMail: request.getToMail()) {
                mailingManager.sendMail(
                        user.getEmailId(),
                        user.getName(),
                        toMail,
                        request.getSubject(),
                        request.getContent(),
                        request.isHTML()
                );
            }

            response = MessageResponse.builder()
                    .message("Mail has been sent to all the entities.")
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response = MessageResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
