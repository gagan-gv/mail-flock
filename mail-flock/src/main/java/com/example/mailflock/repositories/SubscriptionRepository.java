package com.example.mailflock.repositories;

import com.example.mailflock.models.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscriber, String> {
}
