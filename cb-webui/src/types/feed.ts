export interface FeedItem {
  foodPostId: string;
  foodName: string;
  description: string;
  rating: number;
  price: number;
  thumbnailUrl: string | null;
  placeId: string;
  placeName: string;
  userId: string;
  userName: string;
  fullName: string;
  profileImageUrl: string | null;
  verified: boolean;
  confirmedCount: number;
  notAccurateCount: number;
  commentCount: number;
  myReaction: "CONFIRMED" | "NOT_ACCURATE" | null;
  createdAt: string;
}

export interface PaginatedFeedResponse {
  content: FeedItem[];
  pageable: {
    pageNumber: number;
    pageSize: number;
  };
  last: boolean;
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}
