export default function FeedHeader() {
  return (
    <div className="flex flex-col gap-1 w-full pb-2 select-none">
      <h1 className="text-[26px] sm:text-[30px] font-extrabold text-theme-text tracking-tight transition-colors leading-tight">
        Home Feed
      </h1>
      <p className="text-theme-subtext text-[14px] sm:text-[15.5px] font-medium transition-colors">
        Discover real food experiences from the community.
      </p>
    </div>
  );
}
