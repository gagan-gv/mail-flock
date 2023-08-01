package com.example.mailflock.repositories;

import com.example.mailflock.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailId(String emailId);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
