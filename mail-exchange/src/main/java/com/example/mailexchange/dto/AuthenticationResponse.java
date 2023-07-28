package com.example.mailexchange.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthenticationResponse {
    @JsonAlias(value = "msg")
    private String message;

    @JsonAlias(value = "access_token")
    private String accessToken;

    @JsonAlias(value = "refresh_token")
    private String refreshToken;
}
