package com.example.mailflock.services;

import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.dto.UserUpdateRequest;
import com.example.mailflock.models.User;
import com.example.mailflock.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        UserUpdateRequest request = new UserUpdateRequest();
        request.setName("Test");
        request.setUsername("test_user");
        request.setPassword("TestPass1@");

        User user = new User();
        user.setId(1L);
        user.setName("test");
        user.setPassword("oldpass");
        user.setUsername("tets_u");

        // Act
        when(authentication.getPrincipal()).thenReturn(user);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<MessageResponse> response = userService.updateUser(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User details has been updated", Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals("Test", user.getName());
        assertEquals("test_user", user.getUsername());
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void testUpdateUser_Name_Success() {
        // Arrange
        UserUpdateRequest request = new UserUpdateRequest();
        request.setName("Test");

        User user = new User();
        user.setId(1L);
        user.setName("test");
        user.setPassword("oldpass");
        user.setUsername("tets_u");

        // Act
        when(authentication.getPrincipal()).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<MessageResponse> response = userService.updateUser(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User details has been updated", Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals("Test", user.getName());
    }

    @Test
    void testUpdateUser_Username_Success() {
        // Arrange
        UserUpdateRequest request = new UserUpdateRequest();
        request.setUsername("test");

        User user = new User();
        user.setId(1L);
        user.setName("test");
        user.setPassword("oldpass");
        user.setUsername("tets_u");

        // Act
        when(authentication.getPrincipal()).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<MessageResponse> response = userService.updateUser(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User details has been updated", Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals("test", user.getUsername());
    }

    @Test
    void testUpdateUser_Password_Success() {
        // Arrange
        UserUpdateRequest request = new UserUpdateRequest();
        request.setPassword("Test");

        User user = new User();
        user.setId(1L);
        user.setName("test");
        user.setPassword("oldpass");
        user.setUsername("tets_u");

        // Act
        when(authentication.getPrincipal()).thenReturn(user);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<MessageResponse> response = userService.updateUser(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User details has been updated", Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        // Arrange
        UserUpdateRequest request = new UserUpdateRequest();
        request.setUsername("test");

        // Act
        when(authentication.getPrincipal()).thenThrow(new NullPointerException("User not found"));

        ResponseEntity<MessageResponse> response = userService.updateUser(request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testUpdateUser_InternalServerError() {
        // Arrange
        UserUpdateRequest request = new UserUpdateRequest();
        request.setUsername("test");

        // Act
        when(authentication.getPrincipal()).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<MessageResponse> response = userService.updateUser(request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testDeleteUser_Success() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setName("test");
        user.setPassword("oldpass");
        user.setUsername("tets_u");

        // Act
        when(authentication.getPrincipal()).thenReturn(user);

        ResponseEntity<MessageResponse> response = userService.deleteUser();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("User has been deleted", response.getBody().getMessage());
    }

    @Test
    void testDeleteUser_UserNotFound() {
        // Arrange
        // Act
        when(authentication.getPrincipal()).thenThrow(new NullPointerException("User not found"));

        ResponseEntity<MessageResponse> response = userService.deleteUser();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testDeleteUser_InternalServerError() {
        // Arrange
        // Act
        when(authentication.getPrincipal()).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<MessageResponse> response = userService.deleteUser();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", Objects.requireNonNull(response.getBody()).getMessage());
    }
}
