package com.example.mailflock.services;

import com.example.mailflock.dto.AuthenticationResponse;
import com.example.mailflock.dto.AuthenticationRequest;
import com.example.mailflock.dto.RegistrationRequest;
import com.example.mailflock.models.User;
import com.example.mailflock.repositories.UserRepository;
import com.example.mailflock.services.interfaces.IAuthService;

import io.jsonwebtoken.ExpiredJwtException;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    @Value("${spring.mail.username}")
    private String fromMail;

    private static final String FROM_NAME = "Team Mail Flock";

    private final MailingManager sender;
    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<AuthenticationResponse> registerUser(@NonNull RegistrationRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateKeyException("User with the same name exists, we request you to " +
                    "please try forget password for account retrieval.");
        }

        AuthenticationResponse response;

        try {
            final String otp = sender.generateOTP(6);
            final String verificationMessage = "Hello, " + request.getName() +"\n\n" +
                    "Welcome to Mail Flock.\n" +
                    "Here's OTP for account verification: " + otp + ".\n\n" +
                    "Thank you,\n" +
                    FROM_NAME;

            User user = User.builder()
                    .name(request.getName())
                    .username(request.getUsername())
                    .emailId(request.getEmailId())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .otp(otp)
                    .build();

            sender.sendMail(
                    fromMail,
                    FROM_NAME,
                    request.getEmailId(),
                    "Mail Exchange - OTP Verification",
                    verificationMessage,
                    false);
            userRepository.save(user);

            response = AuthenticationResponse.builder()
                    .message("User has been saved to the database." +
                            " Mail has been sent for verification.")
                    .build();
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (DuplicateKeyException e) {
            response = AuthenticationResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response = AuthenticationResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<AuthenticationResponse> verifyUser(@NonNull String emailId, @NonNull String otp) {

        AuthenticationResponse response;

        try {
            User user = userRepository.findByEmailId(emailId)
                    .orElseThrow(() -> new NullPointerException("User not found"));

            if(user.getOtp().equals(otp)) {
                user.setVerified(true);
                userRepository.save(user);

                response = AuthenticationResponse.builder()
                        .message("User has been verified")
                        .build();
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }

            response = AuthenticationResponse.builder()
                    .message("Wrong OTP has been entered")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (NullPointerException e) {
            response = AuthenticationResponse.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response = AuthenticationResponse.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<AuthenticationResponse> login(@NonNull AuthenticationRequest credentials) {
        AuthenticationResponse response;

        try {
            String username = credentials.getUsername();
            String password = credentials.getPassword();

            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );

            User user = userRepository.findByUsername(username).orElseThrow(() ->
                    new NullPointerException("Wrong credentials entered")
            );

            if(user.isVerified()) {
                String accessToken = jwtService.generateToken(user);
                String refreshToken = jwtService.generateRefreshToken(user);

                response = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .message("Login Successful")
                        .build();

                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            response = AuthenticationResponse.builder()
                    .message("User is not verified")
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (NullPointerException e) {
            response = AuthenticationResponse.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (IllegalArgumentException e) {
            response = AuthenticationResponse.builder()
                    .message("Unable to get JWT Token")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (ExpiredJwtException e) {
            response = AuthenticationResponse.builder()
                    .message("JWT token has expired")
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            response = AuthenticationResponse.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<AuthenticationResponse> refreshToken(@NonNull HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;

        AuthenticationResponse response;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            response = AuthenticationResponse.builder()
                    .message("Authorization header not found")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);

        if(username != null) {
            UserDetails userDetails = userRepository.findByUsername(username)
                    .orElseThrow();
            if(jwtService.isTokenValid(refreshToken, userDetails)) {
                String accessToken = jwtService.generateToken(userDetails);
                response = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .message("Access token refreshed")
                        .build();

                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        response = AuthenticationResponse.builder()
                .message("Access token is not refreshed")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
