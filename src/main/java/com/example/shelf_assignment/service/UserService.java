package com.example.shelf_assignment.service;

import com.example.shelf_assignment.model.User;
import com.example.shelf_assignment.dto.ResponseDTO;

public interface UserService {
    ResponseDTO addUser(User user, String userId);
    ResponseDTO getUserById(String userId);
    ResponseDTO updateUser(String userId, User newUserData);
    ResponseDTO deleteUser(String userId);
}
