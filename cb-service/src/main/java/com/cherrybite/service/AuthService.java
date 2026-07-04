package com.cherrybite.service;

import com.cherrybite.payload.RefreshTokenRequest;
import com.cherrybite.payload.RegisterRequest;
import com.cherrybite.payload.response.AuthResponse;

public interface AuthService {

	AuthResponse register(RegisterRequest request);
	
	AuthResponse refreshAccessToken(RefreshTokenRequest refreshTokenValue);
	
	void logout(RefreshTokenRequest refreshTokenValue);
}
