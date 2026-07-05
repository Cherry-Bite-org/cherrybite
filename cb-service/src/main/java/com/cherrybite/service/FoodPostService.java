package com.cherrybite.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.cherrybite.payload.CreateFoodPostRequest;
import com.cherrybite.payload.response.CreateFoodPostResponse;

public interface FoodPostService {

	CreateFoodPostResponse createFoodPost(CreateFoodPostRequest request);

	String uploadFoodImages(UUID foodPostId, List<MultipartFile> files);
}
