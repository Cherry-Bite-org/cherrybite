package com.cherrybite.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cherrybite.entity.User;
import com.cherrybite.exception.ResourceNotFoundException;
import com.cherrybite.exception.UserException;
import com.cherrybite.mapper.UserMapper;
import com.cherrybite.payload.UpdateLocationRequest;
import com.cherrybite.payload.UpdateUser;
import com.cherrybite.payload.response.PublicUserProfileResponse;
import com.cherrybite.payload.response.UserResponse;
import com.cherrybite.payload.response.UserSearchResponse;
import com.cherrybite.repository.FollowRepository;
import com.cherrybite.repository.UserRepository;
import com.cherrybite.service.StorageService;
import com.cherrybite.service.UserService;
import com.cherrybite.util.CurrentUser;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StorageService storageService;
	
	@Autowired
	private FollowRepository followRepository;

	@Override
	public UserResponse getCurrentUser() {
		return UserMapper.toUserResponse(getCurrentUserEntity());
	}

	@Override
	public Optional<User> getUserByEmail(String email) {

		Optional<User> user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UserException("User not found");
		}
		return user;
	}

	@Override
	public User getUserById(UUID userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public String updateUserProfile(UpdateUser updateUser) {

		User user = getCurrentUserEntity();

		if (updateUser.getFullName() != null && !updateUser.getFullName().isBlank()) {
			user.setFullName(updateUser.getFullName());
		}

		if (updateUser.getBio() != null) {
			user.setBio(updateUser.getBio());
		}

		userRepository.save(user);

		return "Profile updated successfully";
	}

	protected User getCurrentUserEntity() {

		CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return userRepository.findById(currentUser.getUserId()).orElseThrow(() -> new UserException("User not found"));
	}

	@Override
	public String uploadProfileImage(MultipartFile file) {
		log.info("Started Upload Profile Image");

		User user = getCurrentUserEntity();
		if (file.isEmpty()) {
			throw new RuntimeException("File is empty");
		}

		String contentType = file.getContentType();

		if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png")
				|| contentType.equals("image/webp"))) {

			throw new RuntimeException("Only images allowed");
		}

		if (file.getSize() > 5 * 1024 * 1024) {
			throw new RuntimeException("Maximum 5MB");
		}
		storageService.delete(user.getProfileImageUrl());
		String imageUrl = storageService.upload(file);
		user.setProfileImageUrl(imageUrl);
		userRepository.save(user);
		return imageUrl;
	}

	@Override
	public String saveCurrentLocation(UpdateLocationRequest request) {
		log.info("Save current user location");
		User user = getCurrentUserEntity();

		if (request.getLastLatitude() == null || request.getLastLongitude() == null) {
			throw new UserException("Latitude and Longitude are required");
		}

		if (request.getLastLatitude().compareTo(BigDecimal.valueOf(-90)) < 0
				|| request.getLastLatitude().compareTo(BigDecimal.valueOf(90)) > 0) {
			throw new UserException("Invalid latitude");
		}

		if (request.getLastLongitude().compareTo(BigDecimal.valueOf(-180)) < 0
				|| request.getLastLongitude().compareTo(BigDecimal.valueOf(180)) > 0) {
			throw new UserException("Invalid longitude");
		}

		user.setLastLatitude(request.getLastLatitude());
		user.setLastLongitude(request.getLastLongitude());
		user.setLastLocationUpdatedAt(LocalDateTime.now());
		user.setLocationPermission(true);

		userRepository.save(user);

		return "Location updated successfully";
	}

	@Override
	public PublicUserProfileResponse getPublicUserProfile(String username) {
		log.info("Get user {}", username);

		User user = userRepository.findByUserName(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		PublicUserProfileResponse response = new PublicUserProfileResponse();

		response.setFullName(user.getFullName());
		response.setUserName(user.getUserName());
		response.setProfileImageUrl(user.getProfileImageUrl());
		response.setBio(user.getBio());
		response.setTrustScore(user.getTrustScore());
		response.setReviewCount(user.getReviewCount());
		response.setFollowers(user.getFollowerCount());
		response.setFollowing(user.getFollowingCount());

		return response;
	}

	@Override
	public List<UserSearchResponse> searchUsers(String keyword) {
		User currentUser = getCurrentUserEntity();
		
		if (keyword == null || keyword.isBlank()) {
		    throw new UserException("Keyword is required");
		}
		keyword = keyword.trim();
		log.info("Search user {}", keyword);
		List<User> users = userRepository.searchUsers(keyword);
		
		Set<UUID> followingIds = new HashSet<>(
		        followRepository.findFollowingIds(currentUser.getUserId())
		);

		return users.stream()
		        .map(user -> {

		            UserSearchResponse response = new UserSearchResponse();

		            response.setUserId(user.getUserId());
		            response.setUsername(user.getUserName());
		            response.setFullName(user.getFullName());
		            response.setProfileImageUrl(user.getProfileImageUrl());
		            response.setTrustScore(user.getTrustScore());
		            response.setVerified(user.getIsVerified());

		            response.setFollowing(
		                    followingIds.contains(user.getUserId())
		            );

		            response.setIsCurrentUser(
		                    currentUser.getUserId().equals(user.getUserId())
		            );

		            return response;
		        })
		        .toList();
	}

}
