import { useMutation } from "@tanstack/react-query";
import { authService } from "../services/authService";
import type { SendOtpPayload, SendOtpResponse } from "../types/auth";

export const useSendOtp = () => {
  return useMutation<SendOtpResponse, Error, SendOtpPayload>({
    mutationFn: (payload) => authService.sendOtp(payload),
  });
};
