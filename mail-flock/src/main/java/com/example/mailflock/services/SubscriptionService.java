package com.example.mailflock.services;

import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.dto.SubscriptionContent;
import com.example.mailflock.models.Subscriber;
import com.example.mailflock.repositories.SubscriptionRepository;
import com.example.mailflock.services.interfaces.ISubscriptionService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.mailflock.utils.Constants.FROM_MAIL;
import static com.example.mailflock.utils.Constants.FROM_NAME;
import static com.example.mailflock.utils.Constants.SUBSCRIBE_MESSAGE;
import static com.example.mailflock.utils.Constants.UNSUBSCRIBE_MESSAGE;

@Service
@RequiredArgsConstructor
public class SubscriptionService implements ISubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final MailingManager mailingManager;

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
                    FROM_MAIL,
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
            subscriptionRepository.delete(subscription);

            mailingManager.sendMail(
                    FROM_MAIL,
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
                        FROM_MAIL,
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
