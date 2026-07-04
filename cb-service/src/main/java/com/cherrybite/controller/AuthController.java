package com.cherrybite.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cherrybite.payload.RefreshTokenRequest;
import com.cherrybite.payload.RegisterRequest;
import com.cherrybite.payload.SendOtpRequest;
import com.cherrybite.payload.VerifyOtpRequest;
import com.cherrybite.payload.response.ApiResponse;
import com.cherrybite.payload.response.AuthResponse;
import com.cherrybite.payload.response.VerifyOtpResponse;
import com.cherrybite.repository.UserRepository;
import com.cherrybite.service.AuthService;
import com.cherrybite.service.OtpService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private OtpService otpService;

	@Autowired
	private AuthService authService;

	@Autowired
	private UserRepository userRepository;

	// Remove OTP return Later
	@PostMapping("/send-otp")
	public ResponseEntity<AuthResponse> signup(@RequestBody SendOtpRequest otpRequest) {
		String otp = otpService.sendOtp(otpRequest);
		AuthResponse response = new AuthResponse();
		response.setMessage("OTP sent successfully : " + otp);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<VerifyOtpResponse> verifyOtp(@RequestBody VerifyOtpRequest otpRequest) {
		log.info("Verify OTP Controller");
		VerifyOtpResponse otpResponse = otpService.verifyOtp(otpRequest);
		return ResponseEntity.ok(otpResponse);
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
		AuthResponse response = authService.register(registerRequest);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/check-username")
	public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {

		boolean available = !userRepository.findByUserName(username).isPresent();
		return ResponseEntity.ok(available);
	}
	
	@PostMapping("/refresh-token")
	public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
		AuthResponse response = authService.refreshAccessToken(refreshToken);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<AuthResponse> logout(@RequestBody RefreshTokenRequest refreshToken) {
		authService.logout(refreshToken);
		AuthResponse response = new AuthResponse();
		response.setMessage("Logout Successfully");
		return ResponseEntity.ok(response);
	}
}
