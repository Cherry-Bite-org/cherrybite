import { useEffect, useRef } from "react";
import { useFeed } from "../hooks/useFeed";
import Sidebar from "../components/feed/Sidebar";
import TopNavbar from "../components/feed/TopNavbar";
import MobileNavbar from "../components/feed/MobileNavbar";
import FeedHeader from "../components/feed/FeedHeader";
import FeedCard from "../components/feed/FeedCard";
import LoadingFeed from "../components/feed/LoadingFeed";
import EmptyFeed from "../components/feed/EmptyFeed";
import { AlertCircle, RefreshCcw, Loader2 } from "lucide-react";

export default function HomePage() {
  const {
    data,
    isLoading,
    isError,
    error,
    hasNextPage,
    fetchNextPage,
    isFetchingNextPage,
    refetch,
  } = useFeed(10);

  const observerTarget = useRef<HTMLDivElement | null>(null);

  // Set up native intersection observer for infinite scroll
  useEffect(() => {
    const target = observerTarget.current;
    if (!target || !hasNextPage || isFetchingNextPage) return;

    const observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting) {
          fetchNextPage();
        }
      },
      { threshold: 0.1 }
    );

    observer.observe(target);

    return () => {
      observer.unobserve(target);
    };
  }, [hasNextPage, isFetchingNextPage, fetchNextPage]);

  // Check if all pages combined are empty
  const isFeedEmpty = !data || data.pages.every((page) => page.content.length === 0);

  return (
    <div className="flex h-screen w-full bg-theme-bg text-theme-text transition-colors duration-300 overflow-hidden justify-start">
      {/* LEFT: Navigation Sidebar (Desktop only, 280px width) */}
      <Sidebar />

      {/* RIGHT: Everything else (flex-1) */}
      <div className="flex-1 flex flex-col min-w-0 h-full overflow-hidden">
        {/* Sticky Top Navbar (Spans 100% of the remaining width) */}
        <TopNavbar />

        {/* Scrollable Feed Container (Fills the remaining height and centers content) */}
        <main className="flex-1 overflow-y-auto flex justify-center px-4 sm:px-5 md:px-8 py-6 pb-24 md:pb-8">
          
          {/* Centered content wrapper container (max-width 780px) */}
          <div className="w-full max-w-[780px] flex flex-col gap-6">
            
            {/* Header Title section */}
            <FeedHeader />

            {/* Loading skeleton state */}
            {isLoading && <LoadingFeed />}

            {/* Error fallback state */}
            {isError && (
              <div className="w-full p-8 flex flex-col items-center justify-center text-center gap-4 bg-theme-card border border-red-500/20 rounded-[28px] transition-colors shadow-sm">
                <div className="w-12 h-12 rounded-full bg-red-500/10 flex items-center justify-center text-red-500">
                  <AlertCircle className="w-6 h-6" />
                </div>
                <div className="flex flex-col gap-1">
                  <h3 className="text-[16px] font-extrabold text-theme-text">
                    Failed to load feed
                  </h3>
                  <p className="text-[13px] text-theme-subtext max-w-xs leading-relaxed">
                    {error?.message || "Something went wrong. Please check your connection and try again."}
                  </p>
                </div>
                <button
                  onClick={() => refetch()}
                  className="flex items-center gap-2 px-5 py-2.5 rounded-xl bg-theme-btn-bg hover:bg-theme-btn-hover text-white text-[13px] font-bold shadow-md cursor-pointer transition-all duration-200"
                >
                  <RefreshCcw className="w-4 h-4" />
                  <span>Try Again</span>
                </button>
              </div>
            )}

            {/* Success state rendering pages */}
            {!isLoading && !isError && (
              <>
                {isFeedEmpty ? (
                  <EmptyFeed />
                ) : (
                  <div className="flex flex-col gap-6 w-full">
                    {data.pages.map((page, pageIndex) => (
                      <div key={pageIndex} className="flex flex-col gap-6 w-full">
                        {page.content.map((post) => (
                          <FeedCard key={post.foodPostId} post={post} />
                        ))}
                      </div>
                    ))}
                    
                    {/* Bottom scroll loader marker */}
                    <div ref={observerTarget} className="w-full py-4 flex items-center justify-center">
                      {isFetchingNextPage ? (
                        <div className="flex items-center gap-2 text-theme-subtext text-[13px] font-semibold">
                          <Loader2 className="w-4 h-4 animate-spin text-theme-accent" />
                          <span>Loading more delicacies...</span>
                        </div>
                      ) : hasNextPage ? (
                        <span className="text-[12px] text-theme-subtext/50 font-bold tracking-wide uppercase select-none">
                          Scroll down for more delights
                        </span>
                      ) : (
                        <span className="text-[12px] text-theme-subtext/50 font-bold tracking-wide uppercase select-none">
                          You've seen all food experiences 🍷
                        </span>
                      )}
                    </div>
                  </div>
                )}
              </>
            )}

          </div>
        </main>
      </div>

      {/* Mobile Tab Navigation bar (Mobile only) */}
      <MobileNavbar />
    </div>
  );
}
