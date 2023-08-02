package com.example.mailflock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
public class RegistrationRequest {
    @Pattern(regexp = "^[A-Za-z]+(?:\\s+[A-Za-z]+)*$")
    private String name;

    @Pattern(regexp = "^[a-z_]{5,30}$")
    private String username;

    @Pattern(regexp = "^(?!\\.)[a-zA-Z0-9._%+-]+(?<!\\.)@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String emailId;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,20}$")
    private String password;
}
