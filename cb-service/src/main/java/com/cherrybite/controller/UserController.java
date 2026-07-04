package com.cherrybite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cherrybite.payload.UpdateLocationRequest;
import com.cherrybite.payload.UpdateUser;
import com.cherrybite.payload.response.ApiResponse;
import com.cherrybite.payload.response.ProfileImageResponse;
import com.cherrybite.payload.response.PublicUserProfileResponse;
import com.cherrybite.payload.response.UserResponse;
import com.cherrybite.payload.response.UserSearchResponse;
import com.cherrybite.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/me")
	public ResponseEntity<UserResponse> getCurrentUser() {
		return ResponseEntity.ok(userService.getCurrentUser());
	}

	@PutMapping("/profile")
	public ResponseEntity<ApiResponse> updateUserProfile(@RequestBody UpdateUser updateUser) {
		String message = userService.updateUserProfile(updateUser);
		ApiResponse response = new ApiResponse();
		response.setMessage(message);
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ProfileImageResponse> uploadProfileImage(@RequestParam("file") MultipartFile file) {
		String imageUrl = userService.uploadProfileImage(file);
		ProfileImageResponse profileImageUrl = new ProfileImageResponse();
		profileImageUrl.setProfileImageUrl(imageUrl);
		return ResponseEntity.ok(profileImageUrl);
	}

	@PostMapping("/location")
	public ResponseEntity<ApiResponse> saveCurrentLocation(@RequestBody UpdateLocationRequest request) {
		String message = userService.saveCurrentLocation(request);
		ApiResponse response = new ApiResponse();
		response.setMessage(message);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{username}")
	public ResponseEntity<PublicUserProfileResponse> getPublicUserProfile(@PathVariable String username) {
		return ResponseEntity.ok(userService.getPublicUserProfile(username));
	}

	@GetMapping("/search")
	public ResponseEntity<List<UserSearchResponse>> searchUsers(@RequestParam String keyword) {
		return ResponseEntity.ok(userService.searchUsers(keyword));
	}
}
