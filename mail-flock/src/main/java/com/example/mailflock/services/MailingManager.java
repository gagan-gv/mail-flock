package com.example.mailflock.services;

import com.example.mailflock.services.interfaces.IMailingManager;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailingManager implements IMailingManager {

    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendMail(
            String fromMail,
            String fromName,
            String toMail,
            String subject,
            String message,
            boolean isHTML
    ) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom(fromMail, fromName);
        helper.setTo(toMail);
        helper.setSubject(subject);
        helper.setText(message, isHTML);

        javaMailSender.send(mimeMessage);
    }

    @Override
    public String generateOTP(int length) {
        String otpCharacters = "0123456789";
        Random random = new Random();
        StringBuilder otpBuilder = new StringBuilder();

        if(length <= 0) {
            length = 6;
        }

        for(int ii = 0; ii < length; ii++) {
            int index = random.nextInt(otpCharacters.length());
            char ch = otpCharacters.charAt(index);

            otpBuilder.append(ch);
        }

        return otpBuilder.toString();
    }
}
