import { Sun, Moon } from "lucide-react";
import { useTheme } from "../../hooks/useTheme";

export function ThemeToggle({ className = "" }) {
  const { theme, toggleTheme } = useTheme();

  return (
    <button
      onClick={toggleTheme}
      className={`p-2 rounded-xl border transition-all duration-300 flex items-center justify-center
        ${theme === 'dark' 
          ? 'bg-theme-bg/50 border-theme-border text-theme-subtext hover:text-theme-text hover:bg-theme-white-overlay' 
          : 'bg-white border-theme-border text-theme-text hover:bg-gray-50 shadow-sm'} 
        ${className}`}
      aria-label="Toggle theme"
    >
      {theme === 'dark' ? (
        <Sun className="w-5 h-5 transition-transform hover:rotate-90" />
      ) : (
        <Moon className="w-5 h-5 transition-transform hover:-rotate-12" />
      )}
    </button>
  );
}
