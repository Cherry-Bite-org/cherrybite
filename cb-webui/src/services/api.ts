import axios from "axios";
import { useAuthStore } from "../store/authStore";
import { decodeJwt } from "../utils/jwt";

const api = axios.create({
  baseURL: "http://192.168.1.10:8080/CB",
  headers: {
    "Content-Type": "application/json",
  },
});

// Request Interceptor: Attach Authorization Header
api.interceptors.request.use(
  (config) => {
    const accessToken = useAuthStore.getState().accessToken;
    if (accessToken && config.headers) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => Promise.reject(error),
);

// Flag to prevent multiple concurrent refresh calls
let isRefreshing = false;
let failedQueue: {
  resolve: (token: string | null) => void;
  reject: (err: unknown) => void;
}[] = [];

const processQueue = (error: unknown, token: string | null = null) => {
  failedQueue.forEach((prom) => {
    if (error) {
      prom.reject(error);
    } else {
      prom.resolve(token);
    }
  });
  failedQueue = [];
};

// Response Interceptor: Handle 401 Unauthorized and refresh token
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    console.log("Test");
    // Check if error is 401 Unauthorized (via status code or response body message)
    const isUnauthorized =
      error.response?.status === 401 ||
      error.response?.data?.status === 401 ||
      error.response?.data?.message === "Invalid JWT Token";

    if (isUnauthorized && !originalRequest._retry) {
      console.log("Test11");
      // If we are already refreshing, queue this request
      if (isRefreshing) {
        return new Promise<string | null>((resolve, reject) => {
          failedQueue.push({ resolve, reject });
        })
          .then((token) => {
            if (originalRequest.headers) {
              originalRequest.headers.Authorization = `Bearer ${token}`;
            }
            return api(originalRequest);
          })
          .catch((err) => Promise.reject(err));
      }

      originalRequest._retry = true;
      isRefreshing = true;

      const refreshToken = useAuthStore.getState().refreshToken;

      if (!refreshToken) {
        useAuthStore.getState().logout();
        window.location.href = "/login";
        return Promise.reject(error);
      }

      try {
        // Call the refresh-token endpoint using basic axios
        const refreshResponse = await axios.post(
          "http://localhost:8080/CB/auth/refresh-token",
          {
            refreshToken,
          },
        );

        const { accessToken: newAccessToken, refreshToken: newRefreshToken } =
          refreshResponse.data;
        const decoded = decodeJwt(newAccessToken);

        if (decoded) {
          const finalRefreshToken = newRefreshToken || refreshToken;
          useAuthStore
            .getState()
            .login(newAccessToken, finalRefreshToken, decoded);

          // Resolve pending failed requests in the queue
          processQueue(null, newAccessToken);

          // Retry the original request
          if (originalRequest.headers) {
            originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
          }
          return api(originalRequest);
        } else {
          throw new Error("Failed to decode new access token");
        }
      } catch (refreshError) {
        processQueue(refreshError, null);
        useAuthStore.getState().logout();
        window.location.href = "/login";
        return Promise.reject(refreshError);
      } finally {
        isRefreshing = false;
      }
    }

    return Promise.reject(error);
  },
);

export default api;
