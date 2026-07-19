import { useTheme } from "../../hooks/useTheme";
import { Search, Bell, MapPin, Sun, Moon } from "lucide-react";

export default function TopNavbar() {
  const { theme, toggleTheme } = useTheme();

  return (
    <header className="sticky top-0 w-full bg-theme-bg/80 backdrop-blur-md border-b border-theme-border flex items-center justify-between gap-4 h-20 px-4 md:px-8 select-none transition-all duration-300 z-20">
      
      {/* Search Input Bar (Hidden on ultra-small mobile screens, shown sm and up) */}
      <div className="flex-1 max-w-sm">
        <div className="relative flex items-center w-full">
          <div className="absolute left-4 pointer-events-none text-theme-subtext">
            <Search className="w-4.5 h-4.5" />
          </div>
          <input
            type="text"
            placeholder="Search reviews, dishes, spots..."
            className="w-full bg-theme-input-bg text-theme-text placeholder-theme-subtext/40 border border-theme-border/40 pl-11 pr-4 py-2.5 rounded-2xl text-[13.5px] font-semibold focus:outline-none focus:border-theme-accent focus:ring-1 focus:ring-theme-accent/20 transition-all duration-300 shadow-sm"
          />
        </div>
      </div>

      {/* Right Controls Area */}
      <div className="flex items-center gap-2.5 sm:gap-3.5">
        {/* Location display */}
        <div className="flex items-center gap-1.5 bg-theme-input-bg border border-theme-border/30 px-3.5 py-2 rounded-2xl text-[12px] sm:text-[13px] font-bold text-theme-text transition-all duration-300 shadow-sm">
          <MapPin className="w-4 h-4 text-theme-accent shrink-0" />
          <span className="max-w-[80px] sm:max-w-none truncate leading-none">Austin, TX</span>
        </div>

        {/* Mobile Theme Toggle */}
        <button
          onClick={toggleTheme}
          className="md:hidden p-2.5 rounded-2xl bg-theme-input-bg border border-theme-border/30 text-theme-text hover:bg-theme-border/20 transition-all duration-300 flex items-center justify-center cursor-pointer shadow-sm active:scale-95"
          aria-label="Toggle theme"
        >
          {theme === "dark" ? (
            <Sun className="w-4.5 h-4.5 text-yellow-400" />
          ) : (
            <Moon className="w-4.5 h-4.5 text-theme-primary" />
          )}
        </button>

        {/* Notification Button */}
        <button 
          className="relative p-2.5 rounded-2xl bg-theme-input-bg border border-theme-border/30 text-theme-text hover:bg-theme-border/20 transition-all duration-300 flex items-center justify-center cursor-pointer shadow-sm active:scale-95"
          aria-label="Notifications"
        >
          <Bell className="w-4.5 h-4.5" />
          {/* Active Red Dot */}
          <span className="absolute top-2 right-2 w-2 h-2 bg-theme-accent rounded-full border border-theme-card animate-pulse" />
        </button>
      </div>
    </header>
  );
}
