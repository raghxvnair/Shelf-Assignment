package com.example.shelf_assignment.service.impl;

import com.example.shelf_assignment.model.User;
import com.example.shelf_assignment.dto.ResponseDTO;
import com.example.shelf_assignment.repo.UserRepo;
import com.example.shelf_assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public ResponseDTO addUser(User user, String userId) {
        if (userRepo.existsById(userId)) {
            return new ResponseDTO(false, "User with ID " + userId + " already exists", null);
        }

        user.setUserId(userId);
        User savedUser = userRepo.save(user);
        return new ResponseDTO(true, "User added successfully", savedUser);
    }

    @Override
    public ResponseDTO getUserById(String userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            return new ResponseDTO(true, "User found", user.get());
        } else {
            return new ResponseDTO(false, "User not found with id: " + userId, null);
        }
    }

    @Override
    public ResponseDTO updateUser(String userId, User newUserData) {
        Optional<User> existingUser = userRepo.findById(userId);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setName(newUserData.getName());
            User savedUser = userRepo.save(updatedUser);
            return new ResponseDTO(true, "User updated successfully", savedUser);
        } else {
            return new ResponseDTO(false, "User not found with id: " + userId, null);
        }
    }

    @Override
    public ResponseDTO deleteUser(String userId) {
        if (userRepo.existsById(userId)) {
            userRepo.deleteById(userId);
            return new ResponseDTO(true, "User successfully deleted with id: " + userId, null);
        } else {
            return new ResponseDTO(false, "User not found with id: " + userId, null);
        }
    }
}