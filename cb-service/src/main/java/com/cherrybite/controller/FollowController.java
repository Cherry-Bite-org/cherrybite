package com.cherrybite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cherrybite.payload.response.ApiResponse;
import com.cherrybite.service.FollowService;

@RestController
@RequestMapping("/follow")
public class FollowController {

	@Autowired
	private FollowService followService;

	@PostMapping("/{username}")
	public ResponseEntity<ApiResponse> followUser(@PathVariable String username) {
		String message = followService.followUser(username);
		ApiResponse response = new ApiResponse();
		response.setMessage(message);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{username}/unfollow")
	public ResponseEntity<ApiResponse> unfollowUser(@PathVariable String username) {
		String message = followService.unfollowUser(username);
		ApiResponse response = new ApiResponse();
		response.setMessage(message);
		return ResponseEntity.ok(response);
	}
}
