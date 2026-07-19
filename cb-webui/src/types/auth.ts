export interface SendOtpPayload {
  identifier: string;
}

export interface SendOtpResponse {
  accessToken: string | null;
  refreshToken: string | null;
  message: string;
}

export interface VerifyOtpPayload {
  identifier: string;
  otp: string;
}

export interface VerifyOtpResponse {
  newUser: boolean;
  accessToken: string | null;
  refreshToken: string | null;
  temporaryToken: string | null;
  message: string;
}

export interface RegisterPayload {
  temporaryToken: string;
  fullName: string;
  userName: string;
  email: string | null;
  phoneNumber: string | null;
}

export interface RegisterResponse {
  accessToken: string;
  refreshToken: string;
  message: string;
}

export interface RefreshTokenPayload {
  refreshToken: string;
}

export interface RefreshTokenResponse {
  accessToken: string;
  refreshToken: string | null;
  message: string | null;
}

export interface CurrentUserResponse {
  userId: string;
  username: string;
  fullName: string;
  email: string | null;
  phoneNumber: string | null;
}

export interface LogoutPayload {
  refreshToken: string;
}

export interface LogoutResponse {
  accessToken: null;
  refreshToken: null;
  message: string;
}
