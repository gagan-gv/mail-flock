package com.example.mailflock.services;

import com.example.mailflock.dto.AuthenticationRequest;
import com.example.mailflock.dto.AuthenticationResponse;
import com.example.mailflock.dto.RegistrationRequest;
import com.example.mailflock.models.User;
import com.example.mailflock.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private MailingManager mailingManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock SubscriptionService subscriptionService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        RegistrationRequest request = new RegistrationRequest();
        request.setName("Test");
        request.setUsername("test_user");
        request.setEmailId("test@example.com");
        request.setPassword("testPassword");

        // Act
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmailId(request.getEmailId())).thenReturn(Optional.empty());
        when(mailingManager.generateOTP(anyInt())).thenReturn("123456");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        ResponseEntity<AuthenticationResponse> response = authService.registerUser(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(
                "User has been saved to the database. Mail has been sent for verification.",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testRegisterUser_Subscribed() {
        // Arrange
        RegistrationRequest request = new RegistrationRequest();
        request.setName("Test");
        request.setUsername("test_user");
        request.setEmailId("test@example.com");
        request.setPassword("testPassword");
        request.setSubscribe(true);

        // Act
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmailId(request.getEmailId())).thenReturn(Optional.empty());
        when(mailingManager.generateOTP(anyInt())).thenReturn("123456");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        ResponseEntity<AuthenticationResponse> response = authService.registerUser(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(
                "User has been saved to the database. Mail has been sent for verification.",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testRegisterUser_DuplicateUsername() {
        // Arrange
        RegistrationRequest request = new RegistrationRequest();
        request.setName("Test");
        request.setUsername("test_user");
        request.setEmailId("test@example.com");
        request.setPassword("testPassword");

        // Act
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(new User()));
        when(userRepository.findByEmailId(request.getEmailId())).thenReturn(Optional.empty());

        ResponseEntity<AuthenticationResponse> response = authService.registerUser(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(
                "User with same username exists, we request you to " +
                        "please try forget password for account retrieval.",
                Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testRegisterUser_DuplicateEmail() {
        // Arrange
        RegistrationRequest request = new RegistrationRequest();
        request.setName("Test");
        request.setUsername("test_user");
        request.setEmailId("test@example.com");
        request.setPassword("testPassword");

        // Act
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmailId(request.getEmailId())).thenReturn(Optional.of(new User()));

        ResponseEntity<AuthenticationResponse> response = authService.registerUser(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(
                "User with same email id exists, we request you to " +
                        "please try forget password for account retrieval.",
                Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testRegisterUser_InternalServerError() {
        // Arrange
        RegistrationRequest request = new RegistrationRequest();
        request.setName("Test");
        request.setUsername("test_user");
        request.setEmailId("test@example.com");
        request.setPassword("testPassword");

        // Act
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmailId(request.getEmailId())).thenReturn(Optional.empty());
        when(mailingManager.generateOTP(anyInt())).thenReturn("123456");
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<AuthenticationResponse> response = authService.registerUser(request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testVerifyUser_Success() {
        // Arrange
        String email = "test@example.com";
        String otp = "123456";

        User user = new User();
        user.setEmailId("test@example.com");
        user.setOtp("123456");

        // Act
        when(userRepository.findByEmailId(email)).thenReturn(Optional.of(user));

        ResponseEntity<AuthenticationResponse> response = authService.verifyUser(email, otp);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User has been verified", Objects.requireNonNull(response.getBody()).getMessage());
        assertTrue(user.isVerified());
    }

    @Test
    void testVerifyUser_WrongOTP() {
        // Arrange
        String email = "test@example.com";
        String otp = "654321";

        User user = new User();
        user.setEmailId("test@example.com");
        user.setOtp("123456");

        // Act
        when(userRepository.findByEmailId(email)).thenReturn(Optional.of(user));

        ResponseEntity<AuthenticationResponse> response = authService.verifyUser(email, otp);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Wrong OTP has been entered", Objects.requireNonNull(response.getBody()).getMessage());
        assertFalse(user.isVerified());
    }

    @Test
    void testVerifyUser_UserNotFound() {
        // Arrange
        String email = "test@example.com";
        String otp = "";

        // Act
        when(userRepository.findByEmailId(email)).thenReturn(Optional.empty());

        ResponseEntity<AuthenticationResponse> response = authService.verifyUser(email, otp);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User not found", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testVerifyUser_InternalServerError() {
        // Arrange
        String email = "test@example.com";
        String otp = "123456";

        User user = new User();
        user.setEmailId("test@example.com");
        user.setOtp("123456");

        // Act
        when(userRepository.findByEmailId(email)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<AuthenticationResponse> response = authService.verifyUser(email, otp);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testLoginUser_Success() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setVerified(true);

        // Act
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");

        ResponseEntity<AuthenticationResponse> response = authService.login(
                new AuthenticationRequest(user.getUsername(), user.getPassword())
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login Successful", Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals("accessToken", response.getBody().getAccessToken());
        assertEquals("refreshToken", response.getBody().getRefreshToken());
    }

    @Test
    void testLoginUser_UserNotVerified() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setVerified(false);

        // Act
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        ResponseEntity<AuthenticationResponse> response = authService.login(
                new AuthenticationRequest(user.getUsername(), user.getPassword())
        );

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User is not verified", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testLoginUser_WrongCredentials() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        // Act
        when(authenticationManager.authenticate(any())).thenThrow(new NullPointerException("Wrong credentials entered"));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        ResponseEntity<AuthenticationResponse> response = authService.login(
                new AuthenticationRequest(user.getUsername(), user.getPassword())
        );

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Wrong credentials entered", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testLoginUser_JWTTokenNotFound() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        // Act
        when(authenticationManager.authenticate(any())).thenThrow(new IllegalArgumentException("Wrong credentials"));

        ResponseEntity<AuthenticationResponse> response = authService.login(
                new AuthenticationRequest(user.getUsername(), user.getPassword())
        );

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unable to get JWT Token", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testLoginUser_InternalServerError() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        // Act
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByUsername(user.getUsername())).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<AuthenticationResponse> response = authService.login(
                new AuthenticationRequest(user.getUsername(), user.getPassword())
        );

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testRefreshToken_ValidToken() {
        // Arrange
        String refreshToken = "validRefreshToken";
        String username = "testuser";

        User user = new User();
        user.setUsername(username);

        HttpServletRequest request = mock(HttpServletRequest.class);

        // Act
        when(jwtService.extractUsername(refreshToken)).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.of(user));
        when(jwtService.isTokenValid(refreshToken, user)).thenReturn(true);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("testAccessToken");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + refreshToken);

        ResponseEntity<AuthenticationResponse> response = authService.refreshToken(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Access token refreshed", Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals("testAccessToken", response.getBody().getAccessToken());
    }

    @Test
    void testRefreshToken_InvalidToken() {
        // Arrange
        String refreshToken = "invalidRefreshToken";
        HttpServletRequest request = mock(HttpServletRequest.class);

        // Act
        when(jwtService.extractUsername(refreshToken)).thenReturn(null);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        ResponseEntity<AuthenticationResponse> response = authService.refreshToken(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Authorization header not found", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testRefreshToken_TokenNotRefreshed() {
        // Arrange
        String refreshToken = "expiredRefreshToken";
        String username = "testuser";

        User user = new User();
        user.setUsername(username);

        HttpServletRequest request = mock(HttpServletRequest.class);

        // Act
        when(jwtService.extractUsername(refreshToken)).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.of(user));
        when(jwtService.isTokenValid(refreshToken, user)).thenReturn(false);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + refreshToken);

        ResponseEntity<AuthenticationResponse> response = authService.refreshToken(request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Access token is not refreshed", Objects.requireNonNull(response.getBody()).getMessage());
    }
}
