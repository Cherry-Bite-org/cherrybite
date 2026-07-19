import { useState } from "react";
import { Utensils, ImageOff, Loader2 } from "lucide-react";

interface ImageCarouselProps {
  thumbnailUrl: string | null;
  foodName: string;
}

export default function ImageCarousel({ thumbnailUrl, foodName }: ImageCarouselProps) {
  const [loading, setLoading] = useState(!!thumbnailUrl);
  const [error, setError] = useState(false);

  // Premium Placeholder rendering when no image is available
  if (!thumbnailUrl || error) {
    return (
      <div 
        className="w-full aspect-video relative overflow-hidden bg-gradient-to-br from-theme-input-bg to-theme-border/20 flex flex-col items-center justify-center gap-2.5 transition-colors duration-300 rounded-[20px] border border-theme-border/30"
      >
        <div className="w-12 h-12 rounded-full bg-theme-card border border-theme-border flex items-center justify-center shadow-sm">
          {error ? (
            <ImageOff className="w-5 h-5 text-theme-subtext" />
          ) : (
            <Utensils className="w-5 h-5 text-theme-accent" />
          )}
        </div>
        <div className="flex flex-col items-center text-center px-6">
          <span className="text-[13.5px] font-extrabold text-theme-text transition-colors">
            {error ? "Image load failed" : foodName}
          </span>
          <span className="text-[10px] font-bold text-theme-subtext/70 uppercase tracking-wider mt-0.5">
            Cherry Bite Delicious Experience 🍷
          </span>
        </div>
      </div>
    );
  }

  return (
    <div 
      className="w-full aspect-video relative overflow-hidden bg-theme-input-bg transition-colors duration-300 rounded-[20px] border border-theme-border/30"
    >
      {/* Loading Skeleton */}
      {loading && (
        <div className="absolute inset-0 bg-theme-border/20 dark:bg-theme-border/10 animate-pulse flex items-center justify-center">
          <Loader2 className="w-7 h-7 text-theme-accent animate-spin opacity-50" />
        </div>
      )}

      {/* Image Element */}
      <img
        src={thumbnailUrl}
        alt={foodName}
        className={`w-full h-full object-cover transition-all duration-500 ${
          loading ? "opacity-0 scale-95" : "opacity-100 scale-100"
        }`}
        onLoad={() => setLoading(false)}
        onError={() => {
          setLoading(false);
          setError(true);
        }}
      />
    </div>
  );
}
