import { useMutation } from "@tanstack/react-query";
import { authService } from "../services/authService";
import { useAuthStore } from "../store/authStore";
import { toast } from "sonner";
import { useNavigate } from "react-router-dom";

export const useLogout = () => {
  const logoutStore = useAuthStore((state) => state.logout);
  const refreshToken = useAuthStore((state) => state.refreshToken);
  const navigate = useNavigate();

  return useMutation({
    mutationFn: () => authService.logout({ refreshToken: refreshToken || "" }),

    onSuccess: (response) => {
      toast.success(response.message);
      logoutStore();

      setTimeout(() => {
        navigate("/login");
      }, 1000); 
    },

    onError: () => {
      // Even if backend logout fails,
      // remove local session
      logoutStore();
      navigate("/login");
    },
  });
};