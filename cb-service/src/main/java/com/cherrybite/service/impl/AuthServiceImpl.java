package com.cherrybite.service.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherrybite.config.JwtProvider;
import com.cherrybite.entity.OtpVerification;
import com.cherrybite.entity.RefreshToken;
import com.cherrybite.entity.User;
import com.cherrybite.enums.UserRole;
import com.cherrybite.exception.UserException;
import com.cherrybite.payload.RefreshTokenRequest;
import com.cherrybite.payload.RegisterRequest;
import com.cherrybite.payload.response.AuthResponse;
import com.cherrybite.repository.OtpVerificationRepository;
import com.cherrybite.repository.RefreshTokenRepository;
import com.cherrybite.repository.UserRepository;
import com.cherrybite.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

  private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

  @Autowired
  private OtpVerificationRepository otpVerificationRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Override
  public AuthResponse register(RegisterRequest request) {

    log.info("User registration start");

    OtpVerification otpVerification = otpVerificationRepository
        .findTopByTemporaryTokenOrderByCreatedAtDesc(request.getTemporaryToken())
        .orElseThrow(() -> new UserException("Invalid temporary token"));

    if (!Boolean.TRUE.equals(otpVerification.getVerified())) {
      throw new UserException("OTP verification required");
    }

    if (userRepository.findByUserName(request.getUserName()).isPresent()) {
      throw new UserException("Username already exists");
    }

    User user = new User();

    user.setFullName(request.getFullName());
    user.setUserName(request.getUserName());
    user.setUserRole(UserRole.ROLE_USER);

    if (otpVerification.getIdentifier().contains("@")) {

      user.setEmail(otpVerification.getIdentifier());

      if (request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank()) {
        user.setPhoneNumber(request.getPhoneNumber());
      }

    } else {

      user.setPhoneNumber(otpVerification.getIdentifier());

      if (request.getEmail() != null && !request.getEmail().isBlank()) {
        user.setEmail(request.getEmail());
      }
    }

    User savedUser = userRepository.save(user);

    String accessToken = jwtProvider.generateToken(savedUser);
    String refreshToken = createRefreshToken(savedUser);

    AuthResponse response = new AuthResponse();
    response.setAccessToken(accessToken);
    response.setRefreshToken(refreshToken);
    response.setMessage("Account created successfully");

    return response;
  }

  private String createRefreshToken(User user) {

    String token = jwtProvider.generateRefreshToken();

    RefreshToken refreshToken = new RefreshToken();

    refreshToken.setUser(user);
    refreshToken.setToken(token);
    refreshToken.setExpiresAt(LocalDateTime.now().plusDays(30));

    refreshTokenRepository.save(refreshToken);

    return token;
  }

  @Override
  public AuthResponse refreshAccessToken(RefreshTokenRequest refreshTokenValue) {

    RefreshToken refreshToken =
        refreshTokenRepository.findByToken(refreshTokenValue.getRefreshToken())
            .orElseThrow(() -> new UserException("Invalid refresh token"));

    if (Boolean.TRUE.equals(refreshToken.getRevoked())) {

      throw new UserException("Refresh token revoked");
    }

    if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {

      throw new UserException("Refresh token expired");
    }

    String accessToken = jwtProvider.generateToken(refreshToken.getUser());
    AuthResponse response = new AuthResponse();
    response.setAccessToken(accessToken);
    return response;
  }

  @Override
  public void logout(RefreshTokenRequest refreshTokenValue) {

    RefreshToken refreshToken =
        refreshTokenRepository.findByToken(refreshTokenValue.getRefreshToken())
            .orElseThrow(() -> new UserException("Invalid refresh token"));

    refreshToken.setRevoked(true);

    refreshTokenRepository.save(refreshToken);
  }

}
