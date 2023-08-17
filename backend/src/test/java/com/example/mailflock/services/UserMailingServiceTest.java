package com.example.mailflock.services;

import com.example.mailflock.dto.MailRequest;
import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.models.User;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMailingServiceTest {

    @Mock
    private MailingManager mailingManager;

    @InjectMocks
    private UserMailingService userMailingService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @SneakyThrows
    @Test
    void testSendMail_Success() {
        // Arrange
        User user = User.builder()
                .name("User")
                .username("user")
                .emailId("user@example.com")
                .isVerified(true)
                .otp("123456")
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null)
        );

        MailRequest request = MailRequest.builder()
                .toMail(new String[] {"user@example.com"})
                .subject("Test")
                .content("Test")
                .isHTML(false)
                .build();

        // Act
        ResponseEntity<MessageResponse> response = userMailingService.sendMail(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Mail has been sent to all the entities.", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testSendMail_UsernameNotFoundException() {
        // Arrange
        SecurityContextHolder.clearContext();

        MailRequest mailRequest = new MailRequest();
        mailRequest.setToMail(new String[]{"test@example.com"});
        mailRequest.setSubject("Test Subject");
        mailRequest.setContent("Test Content");
        mailRequest.setHTML(false);

        // Act
        ResponseEntity<MessageResponse> response = userMailingService.sendMail(mailRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(
                "Cannot invoke \"org.springframework.security.core.Authentication.getPrincipal()\" " +
                        "because the return value of \"org.springframework.security.core.context.SecurityContext." +
                        "getAuthentication()\" is null",
                Objects.requireNonNull(response.getBody()).getMessage());
    }
}
