package com.example.mailflock.services.interfaces;

import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.dto.UserUpdateRequest;

import org.springframework.http.ResponseEntity;

public interface IUserService {
    /**
     * Updates user  attributes
     * @param updateRequest Request contains only updatable attributes
     * @return State based response
     */
    ResponseEntity<MessageResponse> updateUser(UserUpdateRequest updateRequest);

    /**
     * Deletes the user
     * @return State based response
     */
    ResponseEntity<MessageResponse> deleteUser();
}
