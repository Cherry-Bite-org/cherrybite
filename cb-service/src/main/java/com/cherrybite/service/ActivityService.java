package com.cherrybite.service;

import org.springframework.data.domain.Page;

import com.cherrybite.entity.Comment;
import com.cherrybite.entity.FoodPost;
import com.cherrybite.entity.User;
import com.cherrybite.enums.ActivityType;
import com.cherrybite.payload.response.ActivityResponse;

public interface ActivityService {

  void createActivity(User user, FoodPost foodPost, Comment comment, User followUser,
      ActivityType activityType);

  Page<ActivityResponse> getMyActivities(int page, int size);

}
