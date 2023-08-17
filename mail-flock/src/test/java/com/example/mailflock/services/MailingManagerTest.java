package com.example.mailflock.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MailingManagerTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private MailingManager mailingManager;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMail_NonHTML() throws MessagingException, UnsupportedEncodingException {
        String fromMail = "sender@example.com";
        String fromName = "Sender";
        String toMail = "recipient@example.com";
        String subject = "Test Subject";
        String message = "Test Message";
        boolean isHTML = false;

        MimeMessage mimeMessage = mock(MimeMessage.class);
        new MimeMessageHelper(mimeMessage);

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        mailingManager.sendMail(fromMail, fromName, toMail, subject, message, isHTML);
    }

    @Test
    void testSendMail_HTML() throws MessagingException, UnsupportedEncodingException {
        String fromMail = "sender@example.com";
        String fromName = "Sender";
        String toMail = "recipient@example.com";
        String subject = "Test Subject";
        String message = "Test Message";
        boolean isHTML = true;

        MimeMessage mimeMessage = mock(MimeMessage.class);
        new MimeMessageHelper(mimeMessage);

        Context context = new Context();
        context.setVariable("content", message);
        String processedString = "<html><body>" + message + "</body></html>";

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn(processedString);

        mailingManager.sendMail(fromMail, fromName, toMail, subject, message, isHTML);
    }

    @Test
    void testGenerateOTP() {
        int length = 6;
        String otp = mailingManager.generateOTP(length);

        assertEquals(length, otp.length());
    }

    @Test
    void testGenerateOTP_lengthLessThan1() {
        int length = 0;
        String otp = mailingManager.generateOTP(length);

        assertEquals(6, otp.length());
    }
}
