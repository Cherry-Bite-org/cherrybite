import { useAuthStore } from "../store/authStore";
import { useQuery } from "@tanstack/react-query";
import { authService } from "../services/authService";
import { LogOut, Sun, Moon } from "lucide-react";
import { useTheme } from "../hooks/useTheme";
import { useLogout } from "../hooks/useLogout";

export default function HomePage() {
  const user = useAuthStore((state) => state.user);
  const refreshToken = useAuthStore((state) => state.refreshToken);
  const { mutate: logout, isPending } = useLogout();
  const { theme, toggleTheme } = useTheme();

  // Fetch current user details on load
  const { data: currentUser } = useQuery({
    queryKey: ["currentUser"],
    queryFn: () => authService.getCurrentUser(),
    retry: false,
    refetchOnWindowFocus: false,
  });

  return (
    <div className="min-h-screen w-full flex items-center justify-center p-4 bg-theme-bg relative overflow-hidden transition-colors duration-300">
      {/* Floating Theme Toggle */}
      <button
        onClick={toggleTheme}
        className="absolute top-6 right-6 p-3 rounded-full bg-theme-card border border-theme-border text-theme-text hover:bg-theme-bg transition-all duration-300 shadow-md cursor-pointer z-20 flex items-center justify-center"
        aria-label="Toggle theme"
      >
        {theme === "dark" ? (
          <Sun className="w-5 h-5 text-yellow-400" />
        ) : (
          <Moon className="w-5 h-5 text-[#7B1E3A]" />
        )}
      </button>

      {/* Decorative ambient blobs */}
      <div className="absolute -top-40 -left-40 w-96 h-96 rounded-full bg-theme-primary/10 blur-3xl pointer-events-none" />
      <div className="absolute -bottom-40 -right-40 w-96 h-96 rounded-full bg-theme-accent/10 blur-3xl pointer-events-none" />

      <div
        className="w-full max-w-[440px] mx-auto bg-theme-card border border-theme-border shadow-[0_8px_30px_rgba(0,0,0,0.02)] dark:shadow-[0_8px_30px_rgba(0,0,0,0.3)] flex flex-col text-center transition-all duration-300"
        style={{
          borderRadius: "32px",
          padding: "48px 40px",
          gap: "24px",
        }}
      >
        <div className="flex flex-col items-center">
          <div className="w-16 h-16 bg-theme-input-bg rounded-full flex items-center justify-center mb-4 text-3xl transition-colors">
            👋
          </div>
          <h1 className="text-[26px] font-extrabold text-theme-text transition-colors">
            Home Page
          </h1>
          <p className="text-theme-subtext text-[16px] mt-2 font-medium transition-colors">
            Welcome,{" "}
            <span className="text-theme-btn-bg font-bold">
              @{user?.username || "Guest"}
            </span>
            {currentUser?.fullName && (
              <span className="block text-[14px] text-theme-subtext mt-[4px]">
                ({currentUser.fullName})
              </span>
            )}
          </p>
        </div>

        <button
          disabled={isPending}
          onClick={() => {
            if (!refreshToken) return;
            logout({ refreshToken });
          }}
          style={{
            borderRadius: "16px",
            paddingTop: "16px",
            paddingBottom: "16px",
          }}
          className="w-full bg-theme-btn-bg hover:bg-theme-btn-hover text-white font-bold transition-all duration-300 shadow-md shadow-theme-btn-bg/10 hover:shadow-lg hover:shadow-theme-btn-hover/20 active:scale-[0.98] flex items-center justify-center gap-[8px] cursor-pointer"
        >
          <LogOut className="w-[20px] h-[20px]" />
          <span>Logout</span>
        </button>
      </div>
    </div>
  );
}
