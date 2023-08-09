package com.example.mailflock.services.interfaces;

import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.dto.SubscriptionContent;

import org.springframework.http.ResponseEntity;

public interface ISubscriptionService {

    /**
     * Subscribes a user to all the updates and newsletters of mail flock.
     * Send the user a confirmation mail.
     * @param emailId Subscriber's email id
     * @return A message stating if the user has been subscribed or not
     */
    ResponseEntity<MessageResponse> subscribe(String emailId);

    /**
     * unsubscribes a user from all the updates and newsletters of mail flock.
     * Send the user a confirmation mail.
     * @param emailId Subscriber's email id
     * @return A message stating if the user has been unsubscribed or not
     */
    ResponseEntity<MessageResponse> unsubscribe(String emailId);

    /**
     *
     * @param content Mail Content
     * @return A message stating all subscribers have received the mail or not.
     */
    ResponseEntity<MessageResponse> shareWithSubscribers(SubscriptionContent content);
}
