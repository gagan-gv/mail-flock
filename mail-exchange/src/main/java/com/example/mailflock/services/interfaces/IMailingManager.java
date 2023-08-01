package com.example.mailflock.services.interfaces;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface IMailingManager {

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
    ) throws MessagingException, UnsupportedEncodingException;

    /**
     *
     * @param length OTP length
     * @return String of length defined by the entity or uses the default length of 6 digits
     *
     */
    public String generateOTP(int length);
}
