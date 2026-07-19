import { CookingPot } from "lucide-react";

export default function EmptyFeed() {
  return (
    <div className="w-full py-16 px-4 flex flex-col items-center justify-center text-center gap-4 bg-theme-card border border-theme-border rounded-[32px] transition-all duration-300 shadow-sm">
      <div className="w-16 h-16 rounded-full bg-theme-input-bg border border-theme-border flex items-center justify-center text-theme-accent transition-colors duration-300">
        <CookingPot className="w-8 h-8" />
      </div>
      <div className="flex flex-col gap-1">
        <h3 className="text-[18px] font-extrabold text-theme-text transition-colors">
          No food posts yet
        </h3>
        <p className="text-[14px] text-theme-subtext max-w-xs transition-colors">
          Be the first to share your culinary discoveries with the community!
        </p>
      </div>
    </div>
  );
}
