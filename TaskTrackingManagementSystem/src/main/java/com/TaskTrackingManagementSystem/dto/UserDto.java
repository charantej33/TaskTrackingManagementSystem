package com.TaskTrackingManagementSystem.dto;
import java.time.OffsetDateTime;

import com.TaskTrackingManagementSystem.entity.Role;
import com.TaskTrackingManagementSystem.entity.User;

public class UserDto{
    private String id;
    private String username;
    private String email;
    private boolean isActive;
    private Role role;
    private OffsetDateTime createdAt;
   // private OffsetDateTime lastLogin;

    public UserDto(){}
    public UserDto(User user) {
        //this.user = user;
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.isActive = user.isActive();
        this.role = user.getRole();
         this.createdAt = user.getCreatedAt();
        // this.lastLogin = user.getLastLogin();
    }
    //setters 

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
    //getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return isActive;
    }

    public Role getRole() {
        return role;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

}
