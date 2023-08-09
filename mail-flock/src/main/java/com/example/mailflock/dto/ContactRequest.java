package com.example.mailflock.dto;

import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequest {
    @Pattern(regexp = "^[A-Za-z]+(?:\\s+[A-Za-z]+)*$")
    private String name;

    @Pattern(regexp = "^(?!\\.)[a-zA-Z0-9._%+-]+(?<!\\.)@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String emailId;

    private String subject;

    private String content;
}
