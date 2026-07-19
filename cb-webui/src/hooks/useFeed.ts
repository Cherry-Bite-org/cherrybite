import { useInfiniteQuery } from "@tanstack/react-query";
import { feedService } from "../services/feedService";
import type { PaginatedFeedResponse } from "../types/feed";

export const useFeed = (size = 10) => {
  return useInfiniteQuery<PaginatedFeedResponse, Error>({
    queryKey: ["feed"],
    queryFn: ({ pageParam = 0 }) => feedService.getFeed(pageParam as number, size),
    initialPageParam: 0,
    getNextPageParam: (lastPage) => {
      if (lastPage.last || lastPage.empty) {
        return undefined;
      }
      return lastPage.number + 1;
    },
  });
};
