import { useMutation } from "@tanstack/react-query";
import { authService } from "../services/authService";
import type { RegisterPayload, RegisterResponse } from "../types/auth";

export const useRegister = () => {
  return useMutation<RegisterResponse, Error, RegisterPayload>({
    mutationFn: (payload) => authService.register(payload),
  });
};
