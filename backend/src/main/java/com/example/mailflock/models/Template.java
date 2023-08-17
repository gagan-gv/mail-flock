package com.example.mailflock.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "templates")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String subject;

    private String content;

    private boolean isHTML;

    @Builder.Default
    private Long timesUsed = 0L;

    @JoinColumn(name = "user_id")
    @Builder.Default
    private User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
}
