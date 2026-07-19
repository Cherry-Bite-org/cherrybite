import { useMutation } from "@tanstack/react-query";
import { authService } from "../services/authService";
import type { RefreshTokenPayload, RefreshTokenResponse } from "../types/auth";

export const useRefreshToken = () => {
  return useMutation<RefreshTokenResponse, Error, RefreshTokenPayload>({
    mutationFn: (payload) => authService.refreshToken(payload),
  });
};
