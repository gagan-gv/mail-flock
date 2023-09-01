package com.example.mailflock.services;

import com.example.mailflock.dto.ContactRequest;
import com.example.mailflock.dto.DemoRequest;
import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.models.Contact;
import com.example.mailflock.models.Demos;
import com.example.mailflock.repositories.ContactRepository;
import com.example.mailflock.repositories.DemoRepository;
import com.example.mailflock.services.interfaces.IContactService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.example.mailflock.utils.Constants.FROM_NAME;
import static com.example.mailflock.utils.Constants.FROM_MAIL;

@Service
@RequiredArgsConstructor
public class ContactService implements IContactService {

    private final DemoRepository demoRepository;
    private final ContactRepository contactRepository;
    private final MailingManager mailingManager;

    @Override
    public ResponseEntity<MessageResponse> contact(ContactRequest request) {
        MessageResponse response;

        try {
            Contact contact = Contact
                    .builder()
                    .name(request.getName())
                    .emailId(request.getEmailId())
                    .subject(request.getSubject())
                    .content(request.getContent())
                    .build();

            String mailResponse =
                    "Hey " + request.getName() + "\n" +
                    "Subject: " + request.getSubject() + "\n" +
                    "Message: " + request.getContent() + "\n\n" +
                    "We will get back soon.\n\n" +
                    "Regards,\n" + FROM_NAME;

            mailingManager.sendMail(
                    FROM_MAIL,
                    FROM_NAME,
                    request.getEmailId(),
                    "Sharing contact response with " + FROM_NAME,
                    mailResponse,
                    false
            );

            contactRepository.save(contact);

            response = MessageResponse.builder()
                    .message("The contact message has been saved and " +
                            "response has been shared with the requester")
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response = MessageResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<MessageResponse> bookDemo(DemoRequest request) {
        MessageResponse response;

        try {
            Demos demo = Demos.builder()
                    .name(request.getName())
                    .emailId(request.getEmailId())
                    .date(request.getDate())
                    .time(request.getTime())
                    .build();

            mailingManager.sendCalendarInvite(
                    request.getEmailId(),
                    request.getDate(),
                    request.getTime()
            );

            demoRepository.save(demo);

            response = MessageResponse.builder()
                    .message("A demo has been booked and details have been shared via mail")
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response = MessageResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
