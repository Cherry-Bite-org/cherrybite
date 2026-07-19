import { NavLink } from "react-router-dom";
import { useAuthStore } from "../../store/authStore";
import { useTheme } from "../../hooks/useTheme";
import { useLogout } from "../../hooks/useLogout";
import { 
  Home, 
  Users, 
  Compass, 
  Search, 
  PlusSquare, 
  MapPin, 
  Moon, 
  Sun, 
  LogOut,
  Utensils
} from "lucide-react";

export default function Sidebar() {
  const user = useAuthStore((state) => state.user);
  const { mutate: performLogout } = useLogout();
  const { theme, toggleTheme } = useTheme();

  // Navigation Links
  const menuItems = [
    { name: "Home", icon: Home, path: "/home" },
    { name: "Following", icon: Users, path: "/following" },
    { name: "Discover", icon: Compass, path: "/discover" },
    { name: "Search", icon: Search, path: "/search" },
    { name: "Add Review", icon: PlusSquare, path: "/add-review" },
    { name: "Add Place", icon: MapPin, path: "/add-place" },
  ];

  return (
    <aside 
      className="hidden md:flex flex-col h-screen sticky top-0 bg-theme-card border-r border-theme-border w-[280px] shrink-0 transition-all duration-300 select-none z-30"
      style={{ padding: "40px 28px" }}
    >
      {/* Brand Header */}
      <div className="flex items-center gap-3 mb-10 pl-2">
        <div className="w-10 h-10 bg-theme-input-bg border border-theme-border rounded-2xl flex items-center justify-center text-theme-accent transition-colors shadow-sm">
          <Utensils className="w-5.5 h-5.5" />
        </div>
        <span className="font-extrabold text-[22px] text-theme-text tracking-tight transition-colors">
          Cherry Bite 🍷
        </span>
      </div>

      {/* Nav Menu Links */}
      <nav className="flex-1 flex flex-col gap-1.5">
        {menuItems.map((item) => (
          <NavLink
            key={item.name}
            to={item.path}
            onClick={(e) => {
              // Stub non-implemented routes
              if (item.path !== "/home") {
                e.preventDefault();
              }
            }}
            className={({ isActive }) =>
              `flex items-center gap-4 px-4 py-3.5 rounded-2xl text-[14.5px] font-bold border transition-all duration-300 cursor-pointer hover:scale-[1.01] active:scale-[0.99] ${
                isActive && item.path === "/home"
                  ? "bg-theme-btn-bg text-white border-theme-btn-bg shadow-md shadow-theme-btn-bg/15"
                  : "text-theme-subtext border-transparent hover:bg-theme-input-bg hover:text-theme-text"
              }`
            }
          >
            <item.icon className="w-5 h-5 shrink-0" />
            <span>{item.name}</span>
          </NavLink>
        ))}

        {/* Theme Toggle Button */}
        <button
          onClick={toggleTheme}
          className="flex items-center gap-4 px-4 py-3.5 rounded-2xl text-[14.5px] font-bold border border-transparent text-theme-subtext hover:bg-theme-input-bg hover:text-theme-text transition-all duration-300 cursor-pointer text-left w-full mt-3 hover:scale-[1.01] active:scale-[0.99]"
        >
          {theme === "dark" ? (
            <>
              <Sun className="w-5 h-5 text-yellow-400 shrink-0" />
              <span>Light Mode</span>
            </>
          ) : (
            <>
              <Moon className="w-5 h-5 text-[#7B1E3A] shrink-0" />
              <span>Dark Mode</span>
            </>
          )}
        </button>
      </nav>

      {/* Footer Profile & Logout Card */}
      <div className="flex flex-col gap-4 border-t border-theme-border pt-6 mt-auto transition-colors duration-300">
        <div className="flex items-center gap-3.5 px-2">
          {/* Avatar Profile */}
          <div className="w-11 h-11 rounded-2xl bg-theme-input-bg border border-theme-border flex items-center justify-center shrink-0 text-theme-primary font-bold text-[16px] transition-colors shadow-inner">
            {user?.username ? user.username.charAt(0).toUpperCase() : "U"}
          </div>
          {/* Details */}
          <div className="flex flex-col min-w-0 flex-1">
            <span className="text-[14.5px] font-extrabold text-theme-text truncate leading-tight transition-colors">
              @{user?.username || "user"}
            </span>
            <span className="text-[11px] font-semibold text-theme-subtext truncate transition-colors uppercase tracking-wider mt-0.5">
              Member
            </span>
          </div>
        </div>

        {/* Logout Trigger */}
        <button
          onClick={() => performLogout()}
          className="flex items-center justify-center gap-2 w-full py-3 rounded-2xl border border-theme-border text-theme-text bg-theme-input-bg hover:bg-red-500/10 hover:text-red-500 hover:border-red-500/20 text-[12.5px] font-extrabold cursor-pointer transition-all duration-300 hover:scale-[1.01] active:scale-[0.99] shadow-sm"
        >
          <LogOut className="w-4 h-4" />
          <span>Sign Out</span>
        </button>
      </div>
    </aside>
  );
}
