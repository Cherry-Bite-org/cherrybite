package com.cherrybite.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherrybite.config.JwtProvider;
import com.cherrybite.entity.OtpVerification;
import com.cherrybite.entity.RefreshToken;
import com.cherrybite.entity.User;
import com.cherrybite.exception.UserException;
import com.cherrybite.payload.SendOtpRequest;
import com.cherrybite.payload.VerifyOtpRequest;
import com.cherrybite.payload.response.VerifyOtpResponse;
import com.cherrybite.repository.OtpVerificationRepository;
import com.cherrybite.repository.RefreshTokenRepository;
import com.cherrybite.repository.UserRepository;
import com.cherrybite.service.OtpService;

@Service
public class OtpServiceImpl implements OtpService {

  private static final Logger log = LoggerFactory.getLogger(OtpServiceImpl.class);

  @Autowired
  private OtpVerificationRepository otpVerificationRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Override
  public String sendOtp(SendOtpRequest otpRequest) {
    log.info("OTP started to send for mobile: {}", otpRequest.getIdentifier());

    Optional<OtpVerification> existingOtp = otpVerificationRepository
        .findTopByIdentifierOrderByCreatedAtDesc(otpRequest.getIdentifier());

    if (existingOtp.isPresent()) {
      existingOtp.get().setVerified(false);
      otpVerificationRepository.save(existingOtp.get());
    }

    String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));

    OtpVerification otpVerification = new OtpVerification();
    otpVerification.setIdentifier(otpRequest.getIdentifier());
    otpVerification.setOtp(otp);
    otpVerification.setCreatedAt(LocalDateTime.now());
    otpVerification.setExpiresAt(LocalDateTime.now().plusMinutes(5));
    otpVerification.setVerified(false);
    otpVerificationRepository.save(otpVerification);

    log.info("OTP send successfully");
    // Later remove return otp
    return otp;
  }

  @Override
  public VerifyOtpResponse verifyOtp(VerifyOtpRequest otpRequest) {

    log.info("Verifying OTP for identifier: {}", otpRequest.getIdentifier());

    OtpVerification otpVerification = otpVerificationRepository
        .findTopByIdentifierOrderByCreatedAtDesc(otpRequest.getIdentifier())
        .orElseThrow(() -> new UserException("OTP not found"));

    if (Boolean.TRUE.equals(otpVerification.getVerified())) {
      throw new UserException("OTP already used");
    }

    if (otpVerification.getExpiresAt().isBefore(LocalDateTime.now())) {
      throw new UserException("OTP expired");
    }

    if (!otpVerification.getOtp().equals(otpRequest.getOtp())) {
      throw new UserException("Invalid OTP");
    }

    otpVerification.setVerified(true);

    Optional<User> existingUser = findUserByIdentifier(otpRequest.getIdentifier());

    VerifyOtpResponse response = new VerifyOtpResponse();

    // Existing User Login
    if (existingUser.isPresent()) {

      otpVerificationRepository.save(otpVerification);

      User user = existingUser.get();

      String accessToken = jwtProvider.generateToken(user);

      String refreshToken = createRefreshToken(user);

      response.setNewUser(false);
      response.setAccessToken(accessToken);
      response.setRefreshToken(refreshToken);
      response.setMessage("Login successful");

      return response;
    }

    // New User Registration Flow
    String temporaryToken = UUID.randomUUID().toString();

    otpVerification.setTemporaryToken(temporaryToken);

    otpVerificationRepository.save(otpVerification);

    response.setNewUser(true);
    response.setTemporaryToken(temporaryToken);
    response.setMessage("OTP verified successfully");

    return response;
  }

  private Optional<User> findUserByIdentifier(String identifier) {

    if (identifier.contains("@")) {
      return userRepository.findByEmail(identifier);
    }

    return userRepository.findByPhoneNumber(identifier);
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
}
