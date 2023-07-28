package com.example.mailexchange.services.interfaces;

import com.example.mailexchange.dto.AuthenticationResponse;
import com.example.mailexchange.dto.AuthenticationRequest;
import com.example.mailexchange.dto.RegistrationRequest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthService {

    /**
     *
     * @param request Request contains required parameters to create a user
     * @return Authentication response
     */
    public ResponseEntity<AuthenticationResponse> registerUser(RegistrationRequest request);

    /**
     *
     * @param emailId User's email ID
     * @param otp OTP sent for verification via mail
     * @return Authentication response
     */
    public ResponseEntity<AuthenticationResponse> verifyUser(String emailId, String otp);

    /**
     *
     * @param credentials User credentials
     * @return Authentication response (if authentication succeeds also returns, JWT Tokens)
     */
    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest credentials);

    /**
     * @param request HttpSerletRequest
     * @return Authentication response stating if access token is refreshed or not
     */
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request);
}
