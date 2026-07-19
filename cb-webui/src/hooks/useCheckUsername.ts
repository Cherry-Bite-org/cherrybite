import { useQuery } from "@tanstack/react-query";
import { authService } from "../services/authService";

export const useCheckUsername = (username: string, enabled: boolean) => {
  return useQuery<boolean, Error>({
    queryKey: ["checkUsername", username],
    queryFn: () => authService.checkUsername(username),
    enabled: enabled && username.length >= 4,
    retry: false,
    refetchOnWindowFocus: false,
  });
};
