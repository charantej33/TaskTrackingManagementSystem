package com.TaskTrackingManagementSystem.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TaskTrackingManagementSystem.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenHash(String tokenHash);;
        void deleteAllByUserId(String userId);
    }
