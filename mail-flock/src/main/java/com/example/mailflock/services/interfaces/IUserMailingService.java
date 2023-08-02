package com.example.mailflock.services.interfaces;


import com.example.mailflock.dto.MailRequest;
import com.example.mailflock.dto.MessageResponse;

import org.springframework.http.ResponseEntity;

public interface IUserMailingService {

    /**
     * @param request Request containing mail contents
     * @return Returns a message as a JSON response
     */
    public ResponseEntity<MessageResponse> sendMail(MailRequest request);
}
