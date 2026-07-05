package com.cherrybite.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cherrybite.entity.FoodItem;
import com.cherrybite.entity.FoodPost;
import com.cherrybite.entity.FoodPostImage;
import com.cherrybite.entity.Place;
import com.cherrybite.entity.User;
import com.cherrybite.exception.ResourceNotFoundException;
import com.cherrybite.exception.UserException;
import com.cherrybite.payload.CreateFoodPostRequest;
import com.cherrybite.payload.response.CreateFoodPostResponse;
import com.cherrybite.repository.FoodItemRepository;
import com.cherrybite.repository.FoodPostImageRepository;
import com.cherrybite.repository.FoodPostRepository;
import com.cherrybite.repository.PlaceRepository;
import com.cherrybite.service.FoodPostService;
import com.cherrybite.service.StorageService;

@Service
public class FoodPostServiceImpl implements FoodPostService {

	@Autowired
	private FoodPostRepository foodPostRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private FoodItemRepository foodItemRepository;

	@Autowired
	private StorageService storageService;

	@Autowired
	private FoodPostImageRepository foodPostImageRepository;

	@Override
	public CreateFoodPostResponse createFoodPost(CreateFoodPostRequest request) {

		User currentUser = userServiceImpl.getCurrentUserEntity();

		Place place = placeRepository.findById(request.getPlaceId())
				.orElseThrow(() -> new ResourceNotFoundException("Place not found"));

		if (request.getRating().compareTo(BigDecimal.ZERO) < 0
				|| request.getRating().compareTo(BigDecimal.valueOf(5)) > 0) {

			throw new UserException("Rating must be between 0 and 5");
		}

		if (request.getFoodName() == null || request.getFoodName().isBlank()) {
			throw new UserException("Food name is required");
		}

		if (request.getPrice() != null && request.getPrice().compareTo(BigDecimal.ZERO) < 0) {
			throw new UserException("Price cannot be negative");
		}

		String foodName = request.getFoodName().trim();

		FoodItem foodItem = foodItemRepository.findByPlaceAndFoodNameIgnoreCase(place, foodName).orElse(null);

		if (foodItem == null) {
			foodItem = new FoodItem();
			foodItem.setPlace(place);
			foodItem.setFoodName(foodName);
			foodItem.setCreatedBy(currentUser);
			foodItem = foodItemRepository.save(foodItem);
		}
		FoodPost foodPost = new FoodPost();
		foodPost.setFoodItem(foodItem);
		foodPost.setCreatedBy(currentUser);
		foodPost.setDescription(request.getDescription() == null ? null : request.getDescription().trim());
		foodPost.setRating(request.getRating());
		foodPost.setPrice(request.getPrice());
		foodPost.setRecommended(false);

		FoodPost savedFoodPost = foodPostRepository.save(foodPost);

		return new CreateFoodPostResponse(savedFoodPost.getFoodPostId(), "Food post created successfully");
	}

	@Override
	public String uploadFoodImages(UUID foodPostId, List<MultipartFile> files) {

		FoodPost foodPost = foodPostRepository.findById(foodPostId)
				.orElseThrow(() -> new ResourceNotFoundException("Food post not found"));

		User currentUser = userServiceImpl.getCurrentUserEntity();

		if (!foodPost.getCreatedBy().getUserId().equals(currentUser.getUserId())) {
			throw new UserException("You are not allowed to upload images to this food post");
		}

		if (files == null || files.isEmpty()) {
			throw new UserException("Please upload at least one image");
		}

		if (files.size() > 10) {
			throw new UserException("Maximum 10 images allowed");
		}

		Integer maxOrder = foodPostImageRepository.findMaxDisplayOrder(foodPost);
		int order = maxOrder + 1;

		for (MultipartFile file : files) {

			String contentType = file.getContentType();

			if (contentType == null || !contentType.startsWith("image/")) {

				throw new UserException("Only image files are allowed");
			}

			String imageUrl = storageService.upload(file, "food-post");

			FoodPostImage image = new FoodPostImage();

			image.setFoodPost(foodPost);
			image.setImageUrl(imageUrl);
			image.setDisplayOrder(order++);

			foodPostImageRepository.save(image);
		}

		return "Images uploaded successfully";
	}

}
