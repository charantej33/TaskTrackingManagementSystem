package com.TaskTrackingManagementSystem.service;

import com.TaskTrackingManagementSystem.dto.LoginRequestDto;
import com.TaskTrackingManagementSystem.dto.UserRequestDto;

public interface AuthService {
    String registerUser(UserRequestDto requestDto);
    String verifyEmail(String token);
    String loginUser(LoginRequestDto loginRequest);
    String refreshToken(String refreshToken);
    String logout(String refreshToken);

}
