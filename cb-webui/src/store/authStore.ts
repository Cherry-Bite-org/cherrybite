import { create } from "zustand";
import { decodeJwt } from "../utils/jwt";
import type { DecodedToken } from "../utils/jwt";

interface AuthState {
  accessToken: string | null;
  refreshToken: string | null;
  temporaryToken: string | null;
  identifier: string | null;
  user: DecodedToken | null;
  isAuthenticated: boolean;
  login: (accessToken: string, refreshToken: string, user: DecodedToken) => void;
  logout: () => void;
  setTemporaryToken: (token: string | null) => void;
  setIdentifier: (identifier: string | null) => void;
}

const getSavedAccessToken = () => localStorage.getItem("accessToken");
const getSavedRefreshToken = () => localStorage.getItem("refreshToken");
const getSavedUser = () => {
  const token = getSavedAccessToken();
  return token ? decodeJwt(token) : null;
};

export const useAuthStore = create<AuthState>((set) => ({
  accessToken: getSavedAccessToken(),
  refreshToken: getSavedRefreshToken(),
  temporaryToken: null,
  identifier: localStorage.getItem("identifier"),
  user: getSavedUser(),
  isAuthenticated: !!getSavedAccessToken(),

  login: (accessToken, refreshToken, user) => {
    localStorage.setItem("accessToken", accessToken);
    localStorage.setItem("refreshToken", refreshToken);
    set({
      accessToken,
      refreshToken,
      user,
      isAuthenticated: true,
      temporaryToken: null,
    });
  },

  logout: () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("identifier");
    set({
      accessToken: null,
      refreshToken: null,
      temporaryToken: null,
      identifier: null,
      user: null,
      isAuthenticated: false,
    });
  },

  setTemporaryToken: (token) => {
    set({ temporaryToken: token });
  },

  setIdentifier: (identifier) => {
    if (identifier) {
      localStorage.setItem("identifier", identifier);
    } else {
      localStorage.removeItem("identifier");
    }
    set({ identifier });
  },
}));
