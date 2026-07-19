import { NavLink } from "react-router-dom";
import { useAuthStore } from "../../store/authStore";
import { useLogout } from "../../hooks/useLogout";
import { Home, Users, Compass, PlusSquare, LogOut } from "lucide-react";

export default function MobileNavbar() {
  const { mutate: performLogout } = useLogout();

  const navItems = [
    { name: "Home", icon: Home, path: "/home" },
    { name: "Following", icon: Users, path: "/following" },
    { name: "Add", icon: PlusSquare, path: "/add-review" },
    { name: "Discover", icon: Compass, path: "/discover" },
  ];

  return (
    <nav className="md:hidden fixed bottom-0 left-0 right-0 h-16 bg-theme-card border-t border-theme-border flex items-center justify-around px-4 z-40 select-none transition-colors duration-300 shadow-[0_-4px_20px_rgba(0,0,0,0.03)] dark:shadow-[0_-4px_20px_rgba(0,0,0,0.3)]">
      {navItems.map((item) => (
        <NavLink
          key={item.name}
          to={item.path}
          onClick={(e) => {
            if (item.path !== "/home") {
              e.preventDefault();
            }
          }}
          className={({ isActive }) =>
            `flex flex-col items-center justify-center p-2 transition-all duration-200 cursor-pointer ${
              isActive && item.path === "/home"
                ? "text-theme-accent scale-105"
                : "text-theme-subtext hover:text-theme-text"
            }`
          }
        >
          <item.icon className="w-6 h-6 shrink-0" />
          <span className="text-[10px] font-bold mt-1 tracking-wide">{item.name}</span>
        </NavLink>
      ))}

      {/* Profile / Logout shortcut on Mobile Bottom Navbar */}
      <button
        onClick={() => performLogout()}
        className="flex flex-col items-center justify-center p-2 text-theme-subtext hover:text-red-500 transition-all duration-200 cursor-pointer"
        aria-label="Sign out"
      >
        <LogOut className="w-6 h-6 shrink-0" />
        <span className="text-[10px] font-bold mt-1 tracking-wide">Sign Out</span>
      </button>
    </nav>
  );
}
