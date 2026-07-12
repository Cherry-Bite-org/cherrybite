package com.cherrybite.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherrybite.entity.Follow;
import com.cherrybite.entity.User;
import com.cherrybite.enums.ActivityType;
import com.cherrybite.enums.NotificationType;
import com.cherrybite.exception.ResourceNotFoundException;
import com.cherrybite.exception.UserException;
import com.cherrybite.payload.response.FollowUserResponse;
import com.cherrybite.repository.FollowRepository;
import com.cherrybite.repository.UserRepository;
import com.cherrybite.service.ActivityService;
import com.cherrybite.service.FollowService;
import com.cherrybite.service.NotificationService;

@Service
public class FollowServiceImpl implements FollowService {
	
	private static final Logger log = LoggerFactory.getLogger(FollowServiceImpl.class);

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FollowRepository followRepository;
	
	@Autowired
	private ActivityService activityService;

	@Override
	public String followUser(String username) {

		User currentUser = userServiceImpl.getCurrentUserEntity();

		User targetUser = userRepository.findByUserName(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (currentUser.getUserId().equals(targetUser.getUserId())) {
			throw new UserException("You cannot follow yourself");
		}

		boolean alreadyFollowing = followRepository.existsByFollowerAndFollowing(currentUser, targetUser);

		if (alreadyFollowing) {
			throw new UserException("Already following this user");
		}

		Follow follow = new Follow();
		follow.setFollower(currentUser);
		follow.setFollowing(targetUser);
		follow.setCreatedAt(LocalDateTime.now());

		followRepository.save(follow);
		
		activityService.createActivity(
		        currentUser,
		        null,
		        null,
		        targetUser,
		        ActivityType.FOLLOW);
		
		try {
		notificationService.createNotification(
				targetUser,
		        currentUser,
		        null,
		        null,
		        NotificationType.FOLLOW);
		} catch (Exception e) {
		    log.error("Failed to create follow notification", e);
		}
		return "User followed successfully";
	}

	@Override
	public String unfollowUser(String username) {

		User currentUser = userServiceImpl.getCurrentUserEntity();

		User targetUser = userRepository.findByUserName(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Follow follow = followRepository.findByFollowerAndFollowing(currentUser, targetUser)
				.orElseThrow(() -> new UserException("You are not following this user"));

		followRepository.delete(follow);

		return "User unfollowed successfully";
	}

	@Override
	public List<FollowUserResponse> getFollowers(UUID userId) {

		User currentUser = userServiceImpl.getCurrentUserEntity();

		User user = userServiceImpl.getUserById(userId);

		List<Follow> followers = followRepository.findByFollowing(user);

		return followers.stream().map(follow -> {

			User follower = follow.getFollower();

			FollowUserResponse response = new FollowUserResponse();

			response.setUserId(follower.getUserId());
			response.setUserName(follower.getUserName());
			response.setFullName(follower.getFullName());
			response.setProfileImageUrl(follower.getProfileImageUrl());
			response.setVerified(follower.getIsVerified());
			response.setTrustScore(follower.getTrustScore());

			response.setFollowing(followRepository.existsByFollowerAndFollowing(currentUser, follower));

			return response;

		}).toList();
	}

	@Override
	public List<FollowUserResponse> getFollowing(UUID userId) {

		User currentUser = userServiceImpl.getCurrentUserEntity();

		User user = userServiceImpl.getUserById(userId);

		List<Follow> following = followRepository.findByFollower(user);

		return following.stream().map(follow -> {

			User followingUser = follow.getFollowing();

			FollowUserResponse response = new FollowUserResponse();

			response.setUserId(followingUser.getUserId());
			response.setUserName(followingUser.getUserName());
			response.setFullName(followingUser.getFullName());
			response.setProfileImageUrl(followingUser.getProfileImageUrl());
			response.setVerified(followingUser.getIsVerified());
			response.setTrustScore(followingUser.getTrustScore());

			response.setFollowing(followRepository.existsByFollowerAndFollowing(currentUser, followingUser));

			return response;

		}).toList();
	}
}
