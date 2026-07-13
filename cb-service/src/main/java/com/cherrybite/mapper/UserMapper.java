package com.cherrybite.mapper;

import com.cherrybite.entity.User;
import com.cherrybite.payload.response.UserResponse;

public class UserMapper {

  public static UserResponse toUserResponse(User user) {

    UserResponse response = new UserResponse();

    response.setUserId(user.getUserId());
    response.setFullName(user.getFullName());
    response.setUserName(user.getUserName());
    response.setEmail(user.getEmail());
    response.setPhoneNumber(user.getPhoneNumber());
    response.setProfileImageUrl(user.getProfileImageUrl());
    response.setBio(user.getBio());
    response.setTrustScore(user.getTrustScore());
    response.setReviewCount(user.getReviewCount());
    response.setFollowerCount(user.getFollowerCount());
    response.setFollowingCount(user.getFollowingCount());
    response.setVerified(user.getIsVerified());

    return response;
  }
}
