package com.example.shelf_assignment.controller;

import com.example.shelf_assignment.annotation.RateLimit;
import com.example.shelf_assignment.dto.ResponseDTO;
import com.example.shelf_assignment.model.User;
import com.example.shelf_assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add/{userId}")
    @RateLimit(limit = 4, duration = 60)
    public ResponseEntity<ResponseDTO> addUser(@RequestBody User user, @PathVariable String userId) {
        if (user.getName() == null || user.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO(false, "User name is required", null));
        }
        
        ResponseDTO response = userService.addUser(user, userId);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT)
            .body(response);
    }

    @GetMapping("/get/{userId}")
    @RateLimit(limit = 4, duration = 60)
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable String userId) {
        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO(false, "User ID is required", null));
        }

        ResponseDTO response = userService.getUserById(userId);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
            .body(response);
    }

    @PutMapping("/update/{userId}")
    @RateLimit(limit = 4, duration = 60)
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable String userId, @RequestBody User newUserData) {
        if (newUserData.getName() == null || newUserData.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO(false, "User name is required for update", null));
        }

        ResponseDTO response = userService.updateUser(userId, newUserData);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
            .body(response);
    }

    @DeleteMapping("/delete/{userId}")
    @RateLimit(limit = 4, duration = 60)
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String userId) {
        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO(false, "User ID is required", null));
        }

        ResponseDTO response = userService.deleteUser(userId);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
            .body(response);
    }
}