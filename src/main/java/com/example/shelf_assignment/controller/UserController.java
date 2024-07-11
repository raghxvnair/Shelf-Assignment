package com.example.shelf_assignment.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.shelf_assignment.model.User;
import com.example.shelf_assignment.repo.UserRepo;
import com.example.shelf_assignment.annotation.RateLimit;
import com.example.shelf_assignment.dto.ResponseDTO;

@RestController
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @RateLimit(limit = 3, duration = 60)
    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable String userId) {
        try {
            Optional<User> user = userRepo.findById(userId);

            if(user.isPresent()) {
                return ResponseEntity.ok(new ResponseDTO(true, "User found", user.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, "User not found with userId: " + userId, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(false, "An error occurred: " + e.getMessage(), null));
        }
    }

    @RateLimit(limit = 3, duration = 60)
    @PostMapping("/addUser/{userId}")
    public ResponseEntity<ResponseDTO> addUser(@RequestBody User user, @PathVariable String userId) {
        try {

            if (userRepo.existsById(userId)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(false, "User with ID " + userId + " already exists", null));
            }

            user.setUserId(userId);
            
            if (user.getName() == null || user.getName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(false, "User name is required", null));
            }
                        
            User userObj = userRepo.save(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(true, "User added successfully", userObj));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(false, "Failed to add user: " + e.getMessage(), null));
        }
    }

    @RateLimit(limit = 3, duration = 60)
    @PutMapping("/updateUserById/{userId}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable String userId, @RequestBody User newUserData) {
        try {
            Optional<User> user = userRepo.findById(userId);
            
            if(user.isPresent()){
                User updatedUserData = user.get(); 
                updatedUserData.setName(newUserData.getName());

                User userObj = userRepo.save(updatedUserData);

                return ResponseEntity.ok(new ResponseDTO(true, "User updated successfully", userObj));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, "User not found with id: " + userId, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(false, "Failed to update user: " + e.getMessage(), null));
        }
    }

    @RateLimit(limit = 3, duration = 60)
    @DeleteMapping("/deleteUserById/{userId}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String userId) {
        try {
            Optional<User> user = userRepo.findById(userId);
            
            if(user.isPresent()) {
                userRepo.deleteById(userId);
                return ResponseEntity.ok(new ResponseDTO(true, "User successfully deleted with id: " + userId, null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, "User not found with id: " + userId, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(false, "Failed to delete user: " + e.getMessage(), null));
        }
    }
}