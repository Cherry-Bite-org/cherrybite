import { Star } from "lucide-react";

interface RatingBadgeProps {
  rating: number;
}

export default function RatingBadge({ rating }: RatingBadgeProps) {
  // Ensure rating is bounded between 0 and 5
  const normalizedRating = Math.min(5, Math.max(0, rating));

  return (
    <div className="flex items-center gap-1 bg-amber-500/10 dark:bg-amber-400/5 text-amber-600 dark:text-amber-400 border border-amber-500/20 dark:border-amber-400/10 px-2.5 py-1 rounded-xl text-[12px] sm:text-[13px] font-bold shrink-0 shadow-sm transition-colors duration-300">
      <Star className="w-[14px] h-[14px] fill-amber-500 stroke-amber-500 dark:fill-amber-400 dark:stroke-amber-400 shrink-0" />
      <span>{normalizedRating.toFixed(1)}</span>
    </div>
  );
}
