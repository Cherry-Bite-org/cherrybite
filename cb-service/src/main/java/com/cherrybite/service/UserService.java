package com.cherrybite.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.cherrybite.entity.User;
import com.cherrybite.payload.UpdateLocationRequest;
import com.cherrybite.payload.UpdateUser;
import com.cherrybite.payload.response.PublicUserProfileResponse;
import com.cherrybite.payload.response.UserResponse;
import com.cherrybite.payload.response.UserSearchResponse;

public interface UserService {

	UserResponse getCurrentUser();

	Optional<User> getUserByEmail(String email);

	User getUserById(UUID userId);

	List<User> getAllUsers();
	
	String updateUserProfile(UpdateUser updateUser);
	
	String uploadProfileImage(MultipartFile file);
	
	String saveCurrentLocation(UpdateLocationRequest request);
	
	PublicUserProfileResponse getPublicUserProfile(String username);
	
	List<UserSearchResponse> searchUsers(String keyword);

}
