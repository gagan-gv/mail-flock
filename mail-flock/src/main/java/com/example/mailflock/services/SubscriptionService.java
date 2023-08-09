package com.example.mailflock.services;

import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.dto.SubscriptionContent;
import com.example.mailflock.models.Subscriber;
import com.example.mailflock.repositories.SubscriptionRepository;
import com.example.mailflock.services.interfaces.ISubscriptionService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService implements ISubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final MailingManager mailingManager;

    @Value("${spring.mail.username}")
    private String fromMail;

    private static final String FROM_NAME = "Team Mail Flock";
    private static final String SUBSCRIBE_MESSAGE = "Hey There,\n" +
            "We hope this email finds you well. " +
            "On behalf of the entire Mail Flock team, " +
            "we want to extend a warm thank you for subscribing to our mailing list. " +
            "We're excited to have you as part of our community!\n" +
            "\n" +
            "By subscribing to our mailing list, you've taken the first step " +
            "towards staying updated on the latest news, updates, and exciting " +
            "developments in the world of Cold Mailing. We're committed to " +
            "providing you with valuable insights, informative content, and exclusive " +
            "offers that we believe you'll find both engaging and beneficial.\n\n" +
            "Regards,\n" + FROM_NAME;
    private static final String UNSUBSCRIBE_MESSAGE = "Hey There,\n" +
            "We hope this email finds you well. We wanted to reach " +
            "out and express our sincere regret for seeing you unsubscribe " +
            "from our mailing list. Your presence has been valued, and we're " +
            "genuinely sorry that we couldn't provide you with content or " +
            "experiences that met your expectations.\n\n" +
            "While you won't be receiving our emails anymore, please know that " +
            "you're always welcome back to our community whenever you feel ready. " +
            "We're committed to learning from this experience and making the " +
            "necessary adjustments to create a more valuable and engaging " +
            "experience for all of our subscribers.\n\n" +
            "If you have any concerns, questions, or feedback, please don't hesitate " +
            "to reach out to our customer support team at support@mailflock.com. We're " +
            "here to listen and assist you in any way we can.\n\n" +
            "Regards,\n" + FROM_NAME;

    @Override
    public ResponseEntity<MessageResponse> subscribe(String emailId) {
        MessageResponse response;

        try {
            if(subscriptionRepository.findById(emailId).isPresent()) {
                response = MessageResponse.builder()
                        .message(emailId + " has already subscribed to our mail list")
                        .build();

                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            Subscriber subscription = Subscriber.builder()
                    .emailId(emailId)
                    .build();
            subscriptionRepository.save(subscription);

            mailingManager.sendMail(
                    fromMail,
                    FROM_NAME,
                    emailId,
                    "Successful Subscriber to Mail Flock's Mail List",
                    SUBSCRIBE_MESSAGE,
                    false
            );

            response = MessageResponse.builder()
                    .message("User has been subscribed to our mail list " +
                            "and a confirmation mail has been sent")
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
    public ResponseEntity<MessageResponse> unsubscribe(String emailId) {
        MessageResponse response;

        try {
            if(subscriptionRepository.findById(emailId).isEmpty()) {
                response = MessageResponse.builder()
                        .message(emailId + " has not subscribed to our mail list")
                        .build();

                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            Subscriber subscription = Subscriber.builder()
                    .emailId(emailId)
                    .build();
            subscriptionRepository.save(subscription);

            mailingManager.sendMail(
                    fromMail,
                    FROM_NAME,
                    emailId,
                    "Unsubscribed from Mail Flock's Mail List",
                    UNSUBSCRIBE_MESSAGE,
                    false
            );

            response = MessageResponse.builder()
                    .message("User has been unsubscribed from our mail list " +
                            "and a confirmation mail has been sent")
                    .build();

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception e) {
            response = MessageResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<MessageResponse> shareWithSubscribers(SubscriptionContent content) {
        MessageResponse response;

        try {
            List<Subscriber> subscribers = subscriptionRepository.findAll();

            for(Subscriber subscriber: subscribers) {
                mailingManager.sendMail(
                        fromMail,
                        FROM_NAME,
                        subscriber.getEmailId(),
                        content.getSubject(),
                        content.getContent(),
                        false
                );
            }

            response = MessageResponse.builder()
                    .message("The content has been shared with all subscribers")
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response = MessageResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
