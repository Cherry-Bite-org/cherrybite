import api from "./api";
import type {
  SendOtpPayload,
  SendOtpResponse,
  VerifyOtpPayload,
  VerifyOtpResponse,
  RegisterPayload,
  RegisterResponse,
  RefreshTokenPayload,
  RefreshTokenResponse,
  CurrentUserResponse,
  LogoutPayload,
  LogoutResponse,
} from "../types/auth";

export const authService = {
  sendOtp: async (payload: SendOtpPayload): Promise<SendOtpResponse> => {
    const response = await api.post<SendOtpResponse>("/auth/send-otp", payload);
    return response.data;
  },

  verifyOtp: async (payload: VerifyOtpPayload): Promise<VerifyOtpResponse> => {
    const response = await api.post<VerifyOtpResponse>("/auth/verify-otp", payload);
    return response.data;
  },

  register: async (payload: RegisterPayload): Promise<RegisterResponse> => {
    const response = await api.post<RegisterResponse>("/auth/register", payload);
    return response.data;
  },

  checkUsername: async (username: string): Promise<boolean> => {
    const response = await api.get<boolean>(`/auth/check-username?username=${encodeURIComponent(username)}`);
    return response.data;
  },

  getCurrentUser: async (): Promise<CurrentUserResponse> => {
    const response = await api.get<CurrentUserResponse>("/users/me");
    return response.data;
  },

  logout: async (payload: { refreshToken: string }): Promise<{ message: string }> => {
    const response = await api.post<{ message: string }>("/auth/logout", payload);
    return response.data;
  },

  refreshToken: async (payload: RefreshTokenPayload): Promise<RefreshTokenResponse> => {
    const response = await api.post<RefreshTokenResponse>("/auth/refresh-token", payload);
    return response.data;
  },

  logout: async (payload: LogoutPayload): Promise<LogoutResponse> => {
    const response = await api.post<LogoutResponse>("/auth/logout",payload);
    return response.data;
  },

};
