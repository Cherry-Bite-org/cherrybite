package com.cherrybite.service;

import com.cherrybite.payload.response.SearchResponse;

public interface SearchService {

	SearchResponse search(String keyword);
}
