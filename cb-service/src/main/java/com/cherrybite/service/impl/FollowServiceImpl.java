package com.cherrybite.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherrybite.entity.Follow;
import com.cherrybite.entity.User;
import com.cherrybite.exception.ResourceNotFoundException;
import com.cherrybite.exception.UserException;
import com.cherrybite.repository.FollowRepository;
import com.cherrybite.repository.UserRepository;
import com.cherrybite.service.FollowService;

@Service
public class FollowServiceImpl implements FollowService {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FollowRepository followRepository;

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

}
