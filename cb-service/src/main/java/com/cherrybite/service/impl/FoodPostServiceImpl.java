package com.cherrybite.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cherrybite.entity.FoodItem;
import com.cherrybite.entity.FoodPost;
import com.cherrybite.entity.FoodPostImage;
import com.cherrybite.entity.FoodPostReaction;
import com.cherrybite.entity.Place;
import com.cherrybite.entity.User;
import com.cherrybite.enums.ActivityType;
import com.cherrybite.enums.CommentStatus;
import com.cherrybite.enums.FoodPostStatus;
import com.cherrybite.enums.FoodReactionType;
import com.cherrybite.exception.ResourceNotFoundException;
import com.cherrybite.exception.UserException;
import com.cherrybite.payload.CreateFoodPostRequest;
import com.cherrybite.payload.UpdateFoodPostRequest;
import com.cherrybite.payload.response.CreateFoodPostResponse;
import com.cherrybite.payload.response.CreatorResponse;
import com.cherrybite.payload.response.FeedResponse;
import com.cherrybite.payload.response.FoodPostImageResponse;
import com.cherrybite.payload.response.FoodPostResponse;
import com.cherrybite.payload.response.NearbyFoodPostResponse;
import com.cherrybite.payload.response.PlaceResponse;
import com.cherrybite.payload.response.UserFoodPostResponse;
import com.cherrybite.repository.CommentRepository;
import com.cherrybite.repository.FoodItemRepository;
import com.cherrybite.repository.FoodPostImageRepository;
import com.cherrybite.repository.FoodPostReactionRepository;
import com.cherrybite.repository.FoodPostRepository;
import com.cherrybite.repository.PlaceRepository;
import com.cherrybite.repository.UserRepository;
import com.cherrybite.service.ActivityService;
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

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FoodPostReactionRepository reactionRepository;

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ActivityService activityService;

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
		
		activityService.createActivity(
		        currentUser,
		        savedFoodPost,
		        null,
		        null,
		        ActivityType.POST);

		return new CreateFoodPostResponse(savedFoodPost.getFoodPostId(), "Food post created successfully");
	}

	@Override
	public String uploadFoodImages(UUID foodPostId, List<MultipartFile> files) {

		FoodPost foodPost = getActiveFoodPost(foodPostId);

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

	@Override
	public FoodPostResponse getFoodPost(UUID foodPostId) {

		FoodPost foodPost = getActiveFoodPost(foodPostId);

		List<FoodPostImage> images = foodPostImageRepository.findByFoodPostOrderByDisplayOrderAsc(foodPost);

		FoodPostResponse response = new FoodPostResponse();

		response.setFoodPostId(foodPost.getFoodPostId());
		response.setFoodName(foodPost.getFoodItem().getFoodName());
		response.setDescription(foodPost.getDescription());
		response.setRating(foodPost.getRating());
		response.setPrice(foodPost.getPrice());
		response.setRecommended(foodPost.getRecommended());
		response.setCreatedAt(foodPost.getCreatedAt());

		CreatorResponse creator = new CreatorResponse();
		creator.setUserId(foodPost.getCreatedBy().getUserId());
		creator.setUserName(foodPost.getCreatedBy().getUserName());
		creator.setFullName(foodPost.getCreatedBy().getFullName());
		creator.setProfileImageUrl(foodPost.getCreatedBy().getProfileImageUrl());
		creator.setVerified(foodPost.getCreatedBy().getIsVerified());

		response.setCreator(creator);

		PlaceResponse place = new PlaceResponse();
		place.setPlaceId(foodPost.getFoodItem().getPlace().getPlaceId());
		place.setName(foodPost.getFoodItem().getPlace().getName());
		place.setAddress(foodPost.getFoodItem().getPlace().getAddress());

		response.setPlace(place);

		List<FoodPostImageResponse> imageResponses = images.stream()
				.map(image -> new FoodPostImageResponse(image.getImageId(), image.getImageUrl())).toList();

		response.setImages(imageResponses);

		// Future modules
		response.setLikeCount(0L);
		response.setCommentCount(0L);
		response.setLiked(false);
		response.setBookmarked(false);

		return response;
	}

	@Override
	public String updateFoodPost(UUID foodPostId, UpdateFoodPostRequest request) {

		User currentUser = userServiceImpl.getCurrentUserEntity();

		FoodPost foodPost = getActiveFoodPost(foodPostId);

		if (!foodPost.getCreatedBy().getUserId().equals(currentUser.getUserId())) {

			throw new UserException("You are not allowed to update this food post");
		}

		if (request.getRating().compareTo(BigDecimal.ZERO) < 0
				|| request.getRating().compareTo(BigDecimal.valueOf(5)) > 0) {

			throw new UserException("Rating must be between 0 and 5");
		}

		if (request.getPrice() != null && request.getPrice().compareTo(BigDecimal.ZERO) < 0) {

			throw new UserException("Price cannot be negative");
		}

		String foodName = request.getFoodName().trim();

		Place place = foodPost.getFoodItem().getPlace();

		FoodItem foodItem = foodItemRepository.findByPlaceAndFoodNameIgnoreCase(place, foodName).orElse(null);

		if (foodItem == null) {

			foodItem = new FoodItem();
			foodItem.setPlace(place);
			foodItem.setFoodName(foodName);
			foodItem.setCreatedBy(currentUser);

			foodItem = foodItemRepository.save(foodItem);
		}

		foodPost.setFoodItem(foodItem);
		foodPost.setDescription(request.getDescription() == null ? null : request.getDescription().trim());

		foodPost.setRating(request.getRating());
		foodPost.setPrice(request.getPrice());

		foodPostRepository.save(foodPost);

		return "Food post updated successfully";
	}

	@Override
	public String deleteFoodPost(UUID foodPostId) {

		User currentUser = userServiceImpl.getCurrentUserEntity();

		FoodPost foodPost = getActiveFoodPost(foodPostId);

		if (!foodPost.getCreatedBy().getUserId().equals(currentUser.getUserId())) {

			throw new UserException("You are not allowed to delete this food post");
		}

		if (foodPost.getStatus() == FoodPostStatus.DELETED) {
			throw new UserException("Food post already deleted");
		}

		foodPost.setStatus(FoodPostStatus.DELETED);
		foodPost.setDeletedAt(LocalDateTime.now());

		foodPostRepository.save(foodPost);

		return "Food post deleted successfully";
	}

	private FoodPost getActiveFoodPost(UUID foodPostId) {
		return foodPostRepository.findByFoodPostIdAndStatus(foodPostId, FoodPostStatus.ACTIVE)
				.orElseThrow(() -> new ResourceNotFoundException("Food post not found"));
	}

	@Override
	public List<UserFoodPostResponse> getUserFoodPosts(String username) {

		User user = userRepository.findByUserName(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		List<FoodPost> foodPosts = foodPostRepository.findByCreatedByAndStatusOrderByCreatedAtDesc(user,
				FoodPostStatus.ACTIVE);

		return foodPosts.stream().map(foodPost -> {

			UserFoodPostResponse response = new UserFoodPostResponse();

			response.setFoodPostId(foodPost.getFoodPostId());

			response.setFoodName(foodPost.getFoodItem().getFoodName());

			response.setRating(foodPost.getRating());

			Optional<FoodPostImage> image = foodPostImageRepository.findFirstByFoodPostOrderByDisplayOrderAsc(foodPost);

			response.setThumbnailUrl(image.map(FoodPostImage::getImageUrl).orElse(null));

			// Future modules
			response.setLikeCount(0L);
			response.setCommentCount(0L);

			return response;

		}).toList();
	}

	@Override
	public List<NearbyFoodPostResponse> getNearbyFoodPosts(BigDecimal latitude, BigDecimal longitude, Double radius) {

		if (radius == null || radius <= 0) {
			throw new UserException("Radius must be greater than zero");
		}

		List<FoodPost> posts = foodPostRepository.findByStatusOrderByCreatedAtDesc(FoodPostStatus.ACTIVE);

		return posts.stream().map(post -> {
			Place place = post.getFoodItem().getPlace();
			double distance = calculateDistance(latitude.doubleValue(), longitude.doubleValue(),
					place.getLatitude().doubleValue(), place.getLongitude().doubleValue());
			if (distance > radius) {
				return null;
			}

			NearbyFoodPostResponse response = new NearbyFoodPostResponse();

			response.setFoodPostId(post.getFoodPostId());
			response.setFoodName(post.getFoodItem().getFoodName());
			response.setRating(post.getRating());
			response.setPrice(post.getPrice());

			response.setDistance(formatDistance(distance));

			response.setPlaceId(place.getPlaceId());
			response.setPlaceName(place.getName());

			response.setUserId(post.getCreatedBy().getUserId());
			response.setUserName(post.getCreatedBy().getUserName());
			response.setProfileImageUrl(post.getCreatedBy().getProfileImageUrl());

			response.setThumbnailUrl(foodPostImageRepository.findFirstByFoodPostOrderByDisplayOrderAsc(post)
					.map(FoodPostImage::getImageUrl).orElse(null));

			return response;

		}).filter(Objects::nonNull).sorted(Comparator.comparing(response -> {

			String distance = response.getDistance();

			if (distance.endsWith(" km")) {
				return Double.parseDouble(distance.replace(" km", ""));
			}

			if (distance.endsWith(" m")) {
				return Double.parseDouble(distance.replace(" m", "")) / 1000;
			}
			return Double.MAX_VALUE;
		})).toList();
	}

	private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		final int EARTH_RADIUS = 6371;
		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);

		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return EARTH_RADIUS * c;
	}

	private String formatDistance(double distance) {
		if (distance < 1) {
			return Math.round(distance * 1000) + " m";
		}
		return String.format("%.1f km", distance);
	}

	@Override
	public Page<FeedResponse> getFeed(int page, int size) {

		User currentUser = userServiceImpl.getCurrentUserEntity();

		Pageable pageable = PageRequest.of(page, size);

		Page<FoodPost> posts = foodPostRepository.findFeed(currentUser.getUserId(), pageable);

		return posts.map(post -> mapFeedResponse(post, currentUser));
	}

	private FeedResponse mapFeedResponse(FoodPost post, User currentUser) {

		FeedResponse response = new FeedResponse();

		response.setFoodPostId(post.getFoodPostId());

		response.setFoodName(post.getFoodItem().getFoodName());

		response.setDescription(post.getDescription());

		response.setRating(post.getRating());

		response.setPrice(post.getPrice());

		response.setPlaceId(post.getFoodItem().getPlace().getPlaceId());

		response.setPlaceName(post.getFoodItem().getPlace().getName());

		response.setUserId(post.getCreatedBy().getUserId());

		response.setUserName(post.getCreatedBy().getUserName());

		response.setFullName(post.getCreatedBy().getFullName());

		response.setProfileImageUrl(post.getCreatedBy().getProfileImageUrl());

		response.setVerified(post.getCreatedBy().getIsVerified());

		response.setThumbnailUrl(getThumbnail(post));

		response.setConfirmedCount(reactionRepository.countByFoodPostAndReactionType(post, FoodReactionType.CONFIRMED));

		response.setNotAccurateCount(
				reactionRepository.countByFoodPostAndReactionType(post, FoodReactionType.NOT_ACCURATE));

		response.setCommentCount(commentRepository.countByFoodPostAndStatus(post, CommentStatus.ACTIVE));

		response.setMyReaction(reactionRepository.findByFoodPostAndUser(post, currentUser)
				.map(FoodPostReaction::getReactionType).orElse(null));

		response.setCreatedAt(post.getCreatedAt());

		return response;
	}

	private String getThumbnail(FoodPost foodPost) {
		return foodPostImageRepository.findFirstByFoodPostOrderByDisplayOrderAsc(foodPost)
				.map(FoodPostImage::getImageUrl).orElse(null);
	}
}
