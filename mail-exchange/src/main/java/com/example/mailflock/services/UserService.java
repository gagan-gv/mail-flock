package com.example.mailflock.services;

import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.dto.UserUpdateRequest;
import com.example.mailflock.models.User;
import com.example.mailflock.repositories.UserRepository;
import com.example.mailflock.services.interfaces.IUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<MessageResponse> updateUser(UserUpdateRequest updateRequest) {
        MessageResponse response;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(user == null) {
                throw new NullPointerException("User not found");
            }

            if(updateRequest.getName() != null) {
                user.setName(updateRequest.getName());
            }

            if(updateRequest.getUsername() != null) {
                user.setUsername(updateRequest.getUsername());
            }

            if(updateRequest.getPassword() != null) {
                String password = passwordEncoder.encode(updateRequest.getPassword());
                user.setPassword(password);
            }

            userRepository.save(user);

            response = MessageResponse.builder()
                    .message("User details has been updated")
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NullPointerException e) {
            response = MessageResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response = MessageResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<MessageResponse> deleteUser() {
        MessageResponse response;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(user == null) {
                throw new NullPointerException("User not found");
            }

            userRepository.delete(user);

            response = MessageResponse.builder()
                    .message("User has been deleted")
                    .build();

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (NullPointerException e) {
            response = MessageResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response = MessageResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
