package com.example.mailexchange.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
public class AuthenticationRequest {
    @Pattern(regexp = "^[a-z_]{5,30}$")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,20}$")
    private String password;
}
