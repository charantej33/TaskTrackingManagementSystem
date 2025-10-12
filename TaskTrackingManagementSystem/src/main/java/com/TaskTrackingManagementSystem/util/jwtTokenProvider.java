package com.TaskTrackingManagementSystem.util;

import com.TaskTrackingManagementSystem.entity.User;

public interface jwtTokenProvider {
 String createAccessToken(String userId, String role);
//   //  String createRefreshToken();
 String hashToken(String rawToken);
//     boolean validateAccessToken(String token);
 String generateToken(User user);

}
