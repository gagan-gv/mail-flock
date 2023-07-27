package com.example.mailexchange.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@RequiredArgsConstructor
public class MailSender {

    private JavaMailSender javaMailSender;

    /**
     *
     * @param fromMail Company's Mail
     * @param fromName Company's Name
     * @param toMail Receiver's Mail
     * @param subject Mail Subject
     * @param message Mail Content, can be HTML
     * @param isHTML Checks if content is in HTML format
     * @throws MessagingException Will be thrown if there was an error while sending the exception
     * @throws UnsupportedEncodingException Will be thrown if the message content can't be encoded
     *
     */
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

    /**
     *
     * @param length OTP length
     * @return String of length defined by the entity or uses the default length of 6 digits
     *
     */
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
