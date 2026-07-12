package com.cherrybite.payload.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cherrybite.enums.ActivityType;

public class ActivityResponse {

    private UUID activityId;

    private ActivityType activityType;

    private LocalDateTime createdAt;

    // Food Post
    private UUID foodPostId;
    
    private String foodName;

    // Comment
    private UUID commentId;
    private String comment;

    // Follow
    private UUID followUserId;
    
    private String followUserName;
    
    private String followUserProfileImage;
    
	public UUID getActivityId() {
		return activityId;
	}
	public void setActivityId(UUID activityId) {
		this.activityId = activityId;
	}
	public ActivityType getActivityType() {
		return activityType;
	}
	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public UUID getFoodPostId() {
		return foodPostId;
	}
	public void setFoodPostId(UUID foodPostId) {
		this.foodPostId = foodPostId;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public UUID getCommentId() {
		return commentId;
	}
	public void setCommentId(UUID commentId) {
		this.commentId = commentId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public UUID getFollowUserId() {
		return followUserId;
	}
	public void setFollowUserId(UUID followUserId) {
		this.followUserId = followUserId;
	}
	public String getFollowUserName() {
		return followUserName;
	}
	public void setFollowUserName(String followUserName) {
		this.followUserName = followUserName;
	}
	public String getFollowUserProfileImage() {
		return followUserProfileImage;
	}
	public void setFollowUserProfileImage(String followUserProfileImage) {
		this.followUserProfileImage = followUserProfileImage;
	}

}
