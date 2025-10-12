package com.TaskTrackingManagementSystem.entity;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "verification_tokens")
public class VerificationToken {
     @Id
    @Column(name = "token", length = 255)
    private String token;

    @Column(name = "user_id", length = 36, nullable = false)
    private String userId;

    @Column(name = "expires_at", nullable = false)
    private OffsetDateTime expiresAt;

    public VerificationToken() {}

    public VerificationToken(String token, String userId, OffsetDateTime expiresAt) {
        this.token = token;
        this.userId = userId;
        this.expiresAt = expiresAt;
    }
    //setters
    public void setToken(String token) {
        this.token = token;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setExpiresAt(OffsetDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
    //getters
    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public OffsetDateTime getExpiresAt() {
        return expiresAt;
    }
}
