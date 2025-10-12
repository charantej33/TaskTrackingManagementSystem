package com.TaskTrackingManagementSystem.service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TaskTrackingManagementSystem.dto.LoginRequestDto;
import com.TaskTrackingManagementSystem.dto.UserRequestDto;
import com.TaskTrackingManagementSystem.entity.RefreshToken;
import com.TaskTrackingManagementSystem.entity.Role;
import com.TaskTrackingManagementSystem.entity.User;
import com.TaskTrackingManagementSystem.entity.VerificationToken;
import com.TaskTrackingManagementSystem.repository.RefreshTokenRepository;
import com.TaskTrackingManagementSystem.repository.UserRepository;
import com.TaskTrackingManagementSystem.repository.VerificationTokenRepository;
import com.TaskTrackingManagementSystem.util.jwtTokenProvider;

import jakarta.transaction.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
    
private final UserRepository userRepository;
@Autowired
    private final VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private final RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final jwtTokenProvider jwtTokenProvider; 


 public AuthServiceImpl(UserRepository userRepository,
                           VerificationTokenRepository verificationTokenRepository,
                           RefreshTokenRepository refreshTokenRepository,
                           PasswordEncoder passwordEncoder,
                           jwtTokenProvider jwtTokenProvider
                          ) {

                    this.userRepository = userRepository;
                    this.verificationTokenRepository = verificationTokenRepository;
                    this.refreshTokenRepository = refreshTokenRepository;
                    this.passwordEncoder = passwordEncoder;
                    this.jwtTokenProvider = jwtTokenProvider;
                  
    }

    @Override
    @Transactional
    public String registerUser(UserRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }
         if (requestDto.getUsername() != null && userRepository.existsByUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("Username already in use");
        }
        User user = new User();
         user.setId(UUID.randomUUID().toString());
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(requestDto.getPassword()));
        user.setActive(false);
        user.setRole(Role.USER); // Use the correct constant or value defined in your Role class

        userRepository.save(user);
          // create verification token (opaque)
        String token = UUID.randomUUID().toString();
        VerificationToken vt = new VerificationToken(token, user.getId(), OffsetDateTime.now().plusHours(24));
        verificationTokenRepository.save(vt);

        // TODO: send email with verification link (enqueue or call email adapter)
        return "Registered. Verification token: " + token;
    }

    @Override
    public String verifyEmail(String token) {
        Optional<VerificationToken> maybe = verificationTokenRepository.findByToken(token);
        if (maybe.isEmpty()) {
            throw new IllegalArgumentException("Invalid token");
        }
        VerificationToken vt = maybe.get();
        if (vt.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Token expired");
        }
        Long verifiedUserId = Long.valueOf(vt.getUserId());
        User verifiedUser = userRepository.findById(verifiedUserId)
                .orElseThrow(() -> new IllegalStateException("User not found for token"));
        verifiedUser.setActive(true);
        userRepository.save(verifiedUser);

        // cleanup token
        verificationTokenRepository.deleteByUserId(verifiedUser.getId());

        // optionally publish UserVerified event
        return "User verified";
    }

    @Override
    public String loginUser(LoginRequestDto loginRequest) {
    Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
    if (userOpt.isEmpty()) {
        throw new IllegalArgumentException("Invalid username or password");
    }
    User user = userOpt.get();
    if (!user.isActive()) {
        throw new IllegalStateException("User account is not verified");
    }
    if (!passwordEncoder.matches(loginRequest.getpassword(), user.getPassword())) {
        throw new IllegalArgumentException("Invalid username or password");
    }

    // Generate JWT access token
    String accessToken = jwtTokenProvider.generateToken(user);

    // Generate refresh token and save
    String refreshTokenStr = UUID.randomUUID().toString();
    RefreshToken refreshToken = new RefreshToken(refreshTokenStr, user.getId(), null, OffsetDateTime.now().plusDays(7));
    refreshTokenRepository.save(refreshToken);

    // Return tokens (could be a JSON string or DTO, here as a simple string for demo)
    return "AccessToken: " + accessToken + ", RefreshToken: " + refreshTokenStr;
    }

    @Override
    public String refreshToken(String refreshToken) {
       String tokenHash = jwtTokenProvider.hashToken(refreshToken);
        RefreshToken rt = refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (rt.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Refresh token expired");
        }

        // rotate: create new access token (and optionally new refresh token)
        String newAccess = jwtTokenProvider.createAccessToken(rt.getUserId(), null);
        return "AccessToken: " + newAccess;
    }

    @Override
    public String logout(String refreshToken) {
    String tokenHash = jwtTokenProvider.hashToken(refreshToken);
        Optional<RefreshToken> maybe = refreshTokenRepository.findByTokenHash(tokenHash);
        maybe.ifPresent(rt -> {
            rt.setExpiresAt(OffsetDateTime.now());
            refreshTokenRepository.save(rt);
        });
        return "Logged out";
    }
    
}
