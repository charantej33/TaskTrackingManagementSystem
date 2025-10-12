package com.TaskTrackingManagementSystem.util;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.TaskTrackingManagementSystem.entity.User;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
@Component
public class JwtTokenImpl implements jwtTokenProvider {

    private final Key signingKey;
    private final long accessExpirationSeconds;
    private final long refreshExpirationSeconds;

    public JwtTokenImpl(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.access-expiration-seconds}") long accessExpirationSeconds,
            @Value("${app.jwt.refresh-expiration-seconds}") long refreshExpirationSeconds
    ) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpirationSeconds = accessExpirationSeconds;
        this.refreshExpirationSeconds = refreshExpirationSeconds;
    }

    @Override
    public String createAccessToken(String email, String role) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessExpirationSeconds);

        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(signingKey, SignatureAlgorithm.HS256);

        if (role != null) {
            builder.claim("role", role);
        }

        return builder.compact();
    }

    @Override
    public String hashToken(String rawToken) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash token", e);
        }
    }

    @Override
    public String generateToken(User user) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(refreshExpirationSeconds);

        JwtBuilder builder = Jwts.builder()
            .setId(UUID.randomUUID().toString())
            .setSubject(user.getEmail())
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(exp))
            .signWith(signingKey, SignatureAlgorithm.HS256);

        if (user.getRole() != null) {
            builder.claim("role", user.getRole());
        }

        return builder.compact();
    }
}
