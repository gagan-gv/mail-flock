package com.example.mailflock.services.interfaces;

import com.example.mailflock.dto.AuthenticationResponse;
import com.example.mailflock.dto.AuthenticationRequest;
import com.example.mailflock.dto.RegistrationRequest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

public interface IAuthService {

    /**
     *
     * @param request Request contains required parameters to create a user
     * @return Authentication response
     */
    public ResponseEntity<AuthenticationResponse> registerUser(@NonNull RegistrationRequest request);

    /**
     *
     * @param emailId User's email ID
     * @param otp OTP sent for verification via mail
     * @return Authentication response
     */
    public ResponseEntity<AuthenticationResponse> verifyUser(@NonNull String emailId, @NonNull String otp);

    /**
     *
     * @param credentials User credentials
     * @return Authentication response (if authentication succeeds also returns, JWT Tokens)
     */
    public ResponseEntity<AuthenticationResponse> login(@NonNull AuthenticationRequest credentials);

    /**
     * @param request HttpSerletRequest
     * @return Authentication response stating if access token is refreshed or not
     */
    public ResponseEntity<AuthenticationResponse> refreshToken(@NonNull HttpServletRequest request);
}
