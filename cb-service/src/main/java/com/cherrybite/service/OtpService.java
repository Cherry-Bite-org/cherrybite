package com.cherrybite.service;

import com.cherrybite.payload.SendOtpRequest;
import com.cherrybite.payload.VerifyOtpRequest;
import com.cherrybite.payload.response.VerifyOtpResponse;

public interface OtpService {

  String sendOtp(SendOtpRequest otpRequest);

  VerifyOtpResponse verifyOtp(VerifyOtpRequest otpRequest);
}
