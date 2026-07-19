import { ThumbsUp, ThumbsDown, MessageSquare } from "lucide-react";

interface ReactionBarProps {
  confirmedCount: number;
  notAccurateCount: number;
  commentCount: number;
  myReaction: "CONFIRMED" | "NOT_ACCURATE" | null;
}

export default function ReactionBar({
  confirmedCount,
  notAccurateCount,
  commentCount,
  myReaction,
}: ReactionBarProps) {
  return (
    <div className="grid grid-cols-3 gap-2 sm:gap-3 w-full border-t border-theme-border/60 pt-4 mt-2 transition-colors duration-300">
      {/* Confirmed Button */}
      <button
        className={`flex items-center justify-center gap-1.5 py-2.5 rounded-2xl text-[12px] sm:text-[13px] font-bold border transition-all duration-300 hover:scale-[1.01] active:scale-[0.99] cursor-pointer w-full ${
          myReaction === "CONFIRMED"
            ? "bg-green-500/10 dark:bg-green-400/5 text-green-600 dark:text-green-400 border-green-500/35 shadow-sm"
            : "bg-theme-input-bg text-theme-subtext border-theme-border/30 hover:bg-theme-border/20 hover:text-theme-text"
        }`}
      >
        <ThumbsUp className={`w-3.5 h-3.5 sm:w-4 sm:h-4 shrink-0 transition-transform ${myReaction === "CONFIRMED" ? "fill-green-500/20 scale-110" : ""}`} />
        <span>Confirm</span>
        <span className="opacity-70 text-[10px] sm:text-[11px] font-bold">({confirmedCount})</span>
      </button>

      {/* Not Accurate Button */}
      <button
        className={`flex items-center justify-center gap-1.5 py-2.5 rounded-2xl text-[12px] sm:text-[13px] font-bold border transition-all duration-300 hover:scale-[1.01] active:scale-[0.99] cursor-pointer w-full ${
          myReaction === "NOT_ACCURATE"
            ? "bg-red-500/10 dark:bg-red-400/5 text-red-600 dark:text-red-400 border-red-500/35 shadow-sm"
            : "bg-theme-input-bg text-theme-subtext border-theme-border/30 hover:bg-theme-border/20 hover:text-theme-text"
        }`}
      >
        <ThumbsDown className={`w-3.5 h-3.5 sm:w-4 sm:h-4 shrink-0 transition-transform ${myReaction === "NOT_ACCURATE" ? "fill-red-500/20 scale-110" : ""}`} />
        <span>Accurate?</span>
        <span className="opacity-70 text-[10px] sm:text-[11px] font-bold">({notAccurateCount})</span>
      </button>

      {/* Comment Button */}
      <button className="flex items-center justify-center gap-1.5 py-2.5 rounded-2xl text-[12px] sm:text-[13px] font-bold border border-theme-border/30 bg-theme-input-bg text-theme-subtext hover:bg-theme-border/20 hover:text-theme-text transition-all duration-300 hover:scale-[1.01] active:scale-[0.99] cursor-pointer w-full">
        <MessageSquare className="w-3.5 h-3.5 sm:w-4 sm:h-4 shrink-0" />
        <span>Comments</span>
        <span className="opacity-70 text-[10px] sm:text-[11px] font-bold">({commentCount})</span>
      </button>
    </div>
  );
}
