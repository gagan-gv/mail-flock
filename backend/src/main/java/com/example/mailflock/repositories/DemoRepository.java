package com.example.mailflock.repositories;

import com.example.mailflock.models.Demos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoRepository extends JpaRepository<Demos, Long> {
}
