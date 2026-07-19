import type { FeedItem } from "../../types/feed";
import UserInfo from "./UserInfo";
import RatingBadge from "./RatingBadge";
import ImageCarousel from "./ImageCarousel";
import PlaceInfo from "./PlaceInfo";
import ReactionBar from "./ReactionBar";

interface FeedCardProps {
  post: FeedItem;
}

export default function FeedCard({ post }: FeedCardProps) {
  return (
    <div 
      className="w-full bg-theme-card border border-theme-border flex flex-col hover:shadow-[0_12px_36px_rgba(0,0,0,0.03)] dark:hover:shadow-[0_12px_36px_rgba(0,0,0,0.2)] transition-all duration-300 p-6"
      style={{
        borderRadius: "28px",
        gap: "24px",
      }}
    >
      {/* Top Header Block: User profile + Rating */}
      <div className="flex items-center justify-between gap-4">
        <UserInfo
          fullName={post.fullName}
          userName={post.userName}
          profileImageUrl={post.profileImageUrl}
          verified={post.verified}
          createdAt={post.createdAt}
        />
        <RatingBadge rating={post.rating} />
      </div>

      {/* Image Carousel */}
      <ImageCarousel
        thumbnailUrl={post.thumbnailUrl}
        foodName={post.foodName}
      />

      {/* Place info (Food name, location place name, price) */}
      <PlaceInfo
        foodName={post.foodName}
        placeName={post.placeName}
        price={post.price}
      />

      {/* Description text */}
      {post.description && (
        <p className="text-[14px] sm:text-[15px] text-theme-text/80 font-normal leading-relaxed transition-colors whitespace-pre-line">
          {post.description}
        </p>
      )}

      {/* Interactive Reaction stats */}
      <ReactionBar
        confirmedCount={post.confirmedCount}
        notAccurateCount={post.notAccurateCount}
        commentCount={post.commentCount}
        myReaction={post.myReaction}
      />
    </div>
  );
}
