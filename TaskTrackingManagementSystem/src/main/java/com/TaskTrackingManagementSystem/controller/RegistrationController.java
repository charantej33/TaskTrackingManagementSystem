package com.TaskTrackingManagementSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TaskTrackingManagementSystem.dto.LoginRequestDto;
import com.TaskTrackingManagementSystem.dto.UserRequestDto;
import com.TaskTrackingManagementSystem.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {
    private AuthService authService;

    public RegistrationController(AuthService authService) {
        this.authService = authService;
    }
    // User Registration endpoint
    @PostMapping("/register")
     public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequestDto requestDto) {
        String message = authService.registerUser(requestDto);
        return ResponseEntity.ok(message);
    }
    //Email Verification endpoint
    @PostMapping("/verify-email")
      public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        String result = authService.verifyEmail(token);
        return ResponseEntity.ok(result);
    }
    // --- Login ---
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        return ResponseEntity.ok(authService.loginUser(loginRequest));
    }

    // --- Refresh Token (optional) ---
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestParam("token") String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    // --- Logout ---
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam("token") String refreshToken) {
        String message = authService.logout(refreshToken);
        return ResponseEntity.ok(message);
    }

}
