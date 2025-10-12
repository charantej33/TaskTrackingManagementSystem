package com.TaskTrackingManagementSystem.entity;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@Entity
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "email"),
           @UniqueConstraint(columnNames = "username")
       })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "username", length = 100)
    @NotBlank
    @Size(max = 100, message = "Username must be at most 100 characters long")
    private String username;


    @Column(name = "passwordHash", nullable = false, unique = true)
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String passwordHash;

    @Email
    @NotBlank
    @Column(name = "email", nullable = false, length = 255)
    private String email;

     @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false, length = 50)
    private Role role;
    
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    // @Column(name = "last_login")
    // private OffsetDateTime lastLogin;
     public User(String id, String username, String passwordHash, String email, boolean isActive, Role role,
     OffsetDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.isActive = isActive;
        this.role = role;
         this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        // this.lastLogin = lastLogin;
    }

    public User() {
        // JPA
    }
    @PrePersist
    public void prePersist() {
       // if (this.id == null) this.id = UUID.randomUUID().toString();
        this.createdAt = OffsetDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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
    //getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return passwordHash;
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


