package com.example.mailflock.services;

import com.example.mailflock.dto.ContactRequest;
import com.example.mailflock.dto.DemoRequest;
import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.models.Contact;
import com.example.mailflock.models.Demos;
import com.example.mailflock.repositories.ContactRepository;
import com.example.mailflock.repositories.DemoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ContactServiceTest {
    @Mock
    private DemoRepository demoRepository;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private MailingManager mailingManager;

    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testContact_Success() {
        // Arrange
        ContactRequest contactRequest = new ContactRequest("John", "john@example.com", "Inquiry", "Hello");

        // Act
        when(contactRepository.save(any(Contact.class))).thenReturn(new Contact());

        ResponseEntity<MessageResponse> response = contactService.contact(contactRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(
                "The contact message has been saved and " +
                        "response has been shared with the requester",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testContact_InternalServerError() {
        // Arrange
        ContactRequest contactRequest = new ContactRequest("John", "john@example.com", "Inquiry", "Hello");

        // Act
        when(contactRepository.save(any(Contact.class))).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<MessageResponse> response = contactService.contact(contactRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(
                "Internal Server Error",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testBookDemo_Success() {
        // Arrange
        DemoRequest demoRequest = new DemoRequest("Alice", "alice@example.com", Date.valueOf("2023-08-10"), Time.valueOf("10:00:00"));

        // Act
        when(demoRepository.save(any(Demos.class))).thenReturn(new Demos());

        ResponseEntity<MessageResponse> response = contactService.bookDemo(demoRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(
                "A demo has been booked and details have been shared via mail",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testBookDemo_InternalServerError() {
        // Arrange
        DemoRequest demoRequest = new DemoRequest("Alice", "alice@example.com", Date.valueOf("2023-08-10"), Time.valueOf("10:00:00"));

        // Act
        when(demoRepository.save(any(Demos.class))).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<MessageResponse> response = contactService.bookDemo(demoRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(
                "Internal Server Error",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }
}
