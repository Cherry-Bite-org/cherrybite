package com.cherrybite.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.cherrybite.payload.CreateFoodPostRequest;
import com.cherrybite.payload.UpdateFoodPostRequest;
import com.cherrybite.payload.response.CreateFoodPostResponse;
import com.cherrybite.payload.response.FeedResponse;
import com.cherrybite.payload.response.FoodPostResponse;
import com.cherrybite.payload.response.NearbyFoodPostResponse;
import com.cherrybite.payload.response.UserFoodPostResponse;

public interface FoodPostService {

	CreateFoodPostResponse createFoodPost(CreateFoodPostRequest request);

	String uploadFoodImages(UUID foodPostId, List<MultipartFile> files);

	FoodPostResponse getFoodPost(UUID foodPostId);

	String updateFoodPost(UUID foodPostId, UpdateFoodPostRequest request);

	String deleteFoodPost(UUID foodPostId);

	List<UserFoodPostResponse> getUserFoodPosts(String username);

	List<NearbyFoodPostResponse> getNearbyFoodPosts(BigDecimal latitude, BigDecimal longitude, Double radius);
	
	Page<FeedResponse> getFeed(int page, int size);
}
