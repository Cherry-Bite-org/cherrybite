package com.cherrybite.service;

import java.util.List;
import java.util.UUID;

import com.cherrybite.payload.response.FollowUserResponse;

public interface FollowService {
	
	String followUser(String username);
	
	String unfollowUser(String username);
	
	List<FollowUserResponse> getFollowers(UUID userId);

	List<FollowUserResponse> getFollowing(UUID userId);

}
