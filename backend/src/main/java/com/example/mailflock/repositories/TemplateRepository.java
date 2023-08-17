package com.example.mailflock.repositories;

import com.example.mailflock.models.Template;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
