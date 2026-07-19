import { useMutation } from "@tanstack/react-query";
import { authService } from "../services/authService";
import { useAuthStore } from "../store/authStore";
import { decodeJwt } from "../utils/jwt";
import type { VerifyOtpPayload, VerifyOtpResponse } from "../types/auth";

export const useVerifyOtp = () => {
  const login = useAuthStore((state) => state.login);
  const setTemporaryToken = useAuthStore((state) => state.setTemporaryToken);

  return useMutation<VerifyOtpResponse, Error, VerifyOtpPayload>({
    mutationFn: (payload) => authService.verifyOtp(payload),
    onSuccess: (data) => {
      if (data.newUser) {
        setTemporaryToken(data.temporaryToken);
      } else if (data.accessToken && data.refreshToken) {
        const decoded = decodeJwt(data.accessToken);
        if (decoded) {
          login(data.accessToken, data.refreshToken, decoded);
        }
      }
    },
  });
};
