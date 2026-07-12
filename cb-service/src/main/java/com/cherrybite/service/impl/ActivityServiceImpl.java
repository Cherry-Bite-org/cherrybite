package com.cherrybite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cherrybite.entity.Activity;
import com.cherrybite.entity.Comment;
import com.cherrybite.entity.FoodPost;
import com.cherrybite.entity.User;
import com.cherrybite.enums.ActivityType;
import com.cherrybite.payload.response.ActivityResponse;
import com.cherrybite.repository.ActivityRepository;
import com.cherrybite.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Override
	public void createActivity(User user, FoodPost foodPost, Comment comment, User followUser,
			ActivityType activityType) {

		Activity activity = new Activity();

		activity.setUser(user);
		activity.setFoodPost(foodPost);
		activity.setComment(comment);
		activity.setFollowUser(followUser);
		activity.setActivityType(activityType);

		activityRepository.save(activity);

	}

	@Override
	public Page<ActivityResponse> getMyActivities(int page, int size) {

		User currentUser = userServiceImpl.getCurrentUserEntity();

		Pageable pageable = PageRequest.of(page, size);

		Page<Activity> activities = activityRepository.findByUserOrderByCreatedAtDesc(currentUser, pageable);

		return activities.map(this::mapActivityResponse);
	}

	private ActivityResponse mapActivityResponse(Activity activity) {

		ActivityResponse response = new ActivityResponse();

		response.setActivityId(activity.getActivityId());

		response.setActivityType(activity.getActivityType());

		response.setCreatedAt(activity.getCreatedAt());

		// Food Post
		if (activity.getFoodPost() != null) {

			response.setFoodPostId(activity.getFoodPost().getFoodPostId());

			response.setFoodName(activity.getFoodPost().getFoodItem().getFoodName());
		}

		// Comment
		if (activity.getComment() != null) {

			response.setCommentId(activity.getComment().getCommentId());

			response.setComment(activity.getComment().getComment());
		}

		// Follow
		if (activity.getFollowUser() != null) {

			response.setFollowUserId(activity.getFollowUser().getUserId());

			response.setFollowUserName(activity.getFollowUser().getUserName());

			response.setFollowUserProfileImage(activity.getFollowUser().getProfileImageUrl());
		}

		return response;
	}
}
