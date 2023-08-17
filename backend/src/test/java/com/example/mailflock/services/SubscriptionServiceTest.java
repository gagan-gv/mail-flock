package com.example.mailflock.services;

import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.dto.SubscriptionContent;
import com.example.mailflock.models.Subscriber;
import com.example.mailflock.repositories.SubscriptionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SubscriptionServiceTest {
    @Mock
    private MailingManager mailingManager;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSubscribe_Success() {
        // Arrange
        String email = "test@example.com";

        Subscriber subscriber = Subscriber.builder()
                .emailId(email)
                .build();

        // Act
        when(subscriptionRepository.findById(email)).thenReturn(Optional.empty());
        when(subscriptionRepository.save(any(Subscriber.class))).thenReturn(subscriber);

        ResponseEntity<MessageResponse> response = subscriptionService.subscribe(email);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(
                "User has been subscribed to our mail list and a confirmation mail has been sent",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testSubscribe_AlreadySubscribed() {
        // Arrange
        String email = "test@example.com";

        Subscriber subscriber = Subscriber.builder()
                .emailId(email)
                .build();

        // Act
        when(subscriptionRepository.findById(email)).thenReturn(Optional.of(subscriber));

        ResponseEntity<MessageResponse> response = subscriptionService.subscribe(email);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(
                "test@example.com has already subscribed to our mail list",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testSubscribe_InternalServerError() {
        // Arrange
        String email = "test@example.com";

        // Act
        when(subscriptionRepository.findById(email)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<MessageResponse> response = subscriptionService.subscribe(email);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(
                "Internal Server Error",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testUnsubscribe_Success() {
        // Arrange
        String email = "test@example.com";

        Subscriber subscriber = Subscriber.builder()
                .emailId(email)
                .build();

        // Act
        when(subscriptionRepository.findById(email)).thenReturn(Optional.of(subscriber));

        ResponseEntity<MessageResponse> response = subscriptionService.unsubscribe(email);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(
                "User has been unsubscribed from our mail list and a confirmation mail has been sent",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testUnsubscribe_SubscriberNotFound() {
        // Arrange
        String email = "test@example.com";

        // Act
        when(subscriptionRepository.findById(email)).thenReturn(Optional.empty());

        ResponseEntity<MessageResponse> response = subscriptionService.unsubscribe(email);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(
                "test@example.com has not subscribed to our mail list",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testUnsubscribe_InternalServerError() {
        // Arrange
        String email = "test@example.com";

        // Act
        when(subscriptionRepository.findById(email)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<MessageResponse> response = subscriptionService.unsubscribe(email);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(
                "Internal Server Error",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testShareWithSubscriber_Success() {
        // Arrange
        SubscriptionContent content = new SubscriptionContent("Test Subject", "Test Content");
        List<Subscriber> subscribers = Arrays.asList(
                new Subscriber("test1@example.com"),
                new Subscriber("test2@example.com")
        );

        // Act
        when(subscriptionRepository.findAll()).thenReturn(subscribers);

        ResponseEntity<MessageResponse> response = subscriptionService.shareWithSubscribers(content);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(
                "The content has been shared with all subscribers",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testShareWithSubscriber_InternalServerError() {
        // Arrange
        SubscriptionContent content = new SubscriptionContent("Test Subject", "Test Content");

        // Act
        when(subscriptionRepository.findAll()).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<MessageResponse> response = subscriptionService.shareWithSubscribers(content);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(
                "Internal Server Error",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }
}
