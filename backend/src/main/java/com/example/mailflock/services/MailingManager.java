package com.example.mailflock.services;

import com.example.mailflock.services.interfaces.IMailingManager;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Random;

import static com.example.mailflock.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class MailingManager implements IMailingManager {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

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

        if(isHTML) {
            Context context = new Context();
            context.setVariable("content", message);
            String processedString = templateEngine.process(EMAIL_TEMPLATE, context);

            helper.setText(processedString, true);
        } else {
            helper.setText(message, false);
        }

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

    @Override
    @Async
    public void sendCalendarInvite(
            String toMail,
            Date date,
            Time time
    ) throws MessagingException,
            UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String icsContent = createICalFile(date, time);
        ByteArrayResource icsAttachment = new ByteArrayResource(icsContent.getBytes());

        helper.setFrom(FROM_MAIL, FROM_NAME);
        helper.setTo(toMail);
        helper.setSubject("Mail Flock Demo Calendar Invite");
        helper.addAttachment("Mail Flock Demo.ics", icsAttachment, "text/calendar");
        helper.setText("Please find the calendar blocker for the Mail Flock Demo attached");

        javaMailSender.send(message);
    }

    private String createICalFile(Date date, Time time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        String formattedDate = dateFormat.format(date);
        String formattedTime = dateFormat.format(time);

        String icsContent = "BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "PRODID:-//Mail Flock//Mail Flock//EN\n" +
                "BEGIN:VEVENT\n" +
                "DTSTART:" + formattedDate + "T" + formattedTime + "\n" +
                "DTEND:" + formattedDate + "T" + formattedTime + "\n" +
                "SUMMARY:Mail Flock Demo\n" +
                "DESCRIPTION:This is a calendar blocker for Mail Flock Demo\n" +
                "UID:" + System.currentTimeMillis() + "\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR\n";

        return icsContent;
    }
}
