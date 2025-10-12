package com.TaskTrackingManagementSystem.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
       @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "user_id", length = 36, nullable = false)
    private String userId;

    @Column(name = "token_hash", nullable = false, length = 255)
    private String tokenHash;

    @Column(name = "expires_at", nullable = false)
    private OffsetDateTime expiresAt;

    public RefreshToken() {}

    public RefreshToken(String id, String userId, String tokenHash, OffsetDateTime expiresAt) {
        this.id = id;
        this.userId = userId;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
    }
//setters
public void setId(String id) {
    this.id = id;
}

public void setUserId(String userId) {
    this.userId = userId;
}

public void setTokenHash(String tokenHash) {
    this.tokenHash = tokenHash;
}

public void setExpiresAt(OffsetDateTime expiresAt) {
    this.expiresAt = expiresAt;
}
//getters
public String getId() {
    return id;
}

public String getUserId() {
    return userId;
}

public String getTokenHash() {
    return tokenHash;
}

public OffsetDateTime getExpiresAt() {
    return expiresAt;
}
}
