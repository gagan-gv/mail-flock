package com.example.mailflock.services.interfaces;

import com.example.mailflock.dto.ContactRequest;
import com.example.mailflock.dto.DemoRequest;
import com.example.mailflock.dto.MessageResponse;

import org.springframework.http.ResponseEntity;

public interface IContactService {

    /**
     * Stores the contact details & reason in the database for the admins to verify
     * and also sends a mail with a copy contact reason to sender.
     * @param request Takes in contact details and the reason
     * @return A message stating whether the communication was successful or not
     */
    ResponseEntity<MessageResponse> contact(ContactRequest request);

    /**
     * Stores the booking details and also sends the user a calendar invite.
     * @param request Takes in booking details
     * @return A message stating whether the booking was successful or not
     */
    ResponseEntity<MessageResponse> bookDemo(DemoRequest request);
}
