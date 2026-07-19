import api from "./api";
import type { PaginatedFeedResponse } from "../types/feed";

export const feedService = {
  getFeed: async (page: number, size: number): Promise<PaginatedFeedResponse> => {
    const response = await api.get<PaginatedFeedResponse>(`/home/feed?page=${page}&size=${size}`);
    return response.data;
  },
};
