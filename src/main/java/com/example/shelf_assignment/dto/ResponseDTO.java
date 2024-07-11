package com.example.shelf_assignment.dto;

import com.example.shelf_assignment.model.User;

public class ResponseDTO {
    private boolean success;
    private String message;
    private User data;

    public ResponseDTO(boolean success, String message, User data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}