package com.TaskTrackingManagementSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TaskTrackingManagementSystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsByEmail(String email);

    public boolean existsByUsername(String username);

    public Optional<User> findByEmail(String email);
}

