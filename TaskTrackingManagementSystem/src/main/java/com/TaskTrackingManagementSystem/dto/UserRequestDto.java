package com.TaskTrackingManagementSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDto {
    
    @NotBlank
    @Size(max = 100, message = "Username must be at most 100 characters long")
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    public UserRequestDto() {}

    public UserRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    //setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //getters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
