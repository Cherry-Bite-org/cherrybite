export default function LoadingFeed() {
  const skeletons = Array(3).fill(0);

  return (
    <div className="flex flex-col gap-6 w-full">
      {skeletons.map((_, i) => (
        <div
          key={i}
          className="w-full bg-theme-card border border-theme-border flex flex-col animate-pulse transition-all duration-300"
          style={{
            borderRadius: "32px",
            padding: "24px",
            gap: "16px",
          }}
        >
          {/* Header Skeleton */}
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-full bg-theme-border/30 dark:bg-theme-border/10 shrink-0" />
            <div className="flex flex-col gap-2 min-w-0 flex-1">
              <div className="h-4 w-32 bg-theme-border/30 dark:bg-theme-border/10 rounded-md" />
              <div className="h-3 w-20 bg-theme-border/30 dark:bg-theme-border/10 rounded-md" />
            </div>
          </div>

          {/* Carousel/Image Skeleton */}
          <div
            className="w-full bg-theme-border/30 dark:bg-theme-border/10"
            style={{ height: "260px", borderRadius: "20px" }}
          />

          {/* Place & Description Skeleton */}
          <div className="flex flex-col gap-2">
            <div className="flex justify-between items-center">
              <div className="h-5 w-40 bg-theme-border/30 dark:bg-theme-border/10 rounded-md" />
              <div className="h-5 w-16 bg-theme-border/30 dark:bg-theme-border/10 rounded-md" />
            </div>
            <div className="h-4 w-28 bg-theme-border/30 dark:bg-theme-border/10 rounded-md" />
            <div className="space-y-1.5 mt-2">
              <div className="h-3.5 w-full bg-theme-border/30 dark:bg-theme-border/10 rounded-md" />
              <div className="h-3.5 w-5/6 bg-theme-border/30 dark:bg-theme-border/10 rounded-md" />
            </div>
          </div>

          {/* Reaction Bar Skeleton */}
          <div className="flex justify-between items-center pt-4 border-t border-theme-border/30">
            <div className="h-9 w-20 bg-theme-border/30 dark:bg-theme-border/10 rounded-xl" />
            <div className="h-9 w-24 bg-theme-border/30 dark:bg-theme-border/10 rounded-xl" />
            <div className="h-9 w-24 bg-theme-border/30 dark:bg-theme-border/10 rounded-xl" />
          </div>
        </div>
      ))}
    </div>
  );
}
