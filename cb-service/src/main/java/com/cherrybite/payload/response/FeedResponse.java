package com.cherrybite.payload.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.cherrybite.enums.FoodReactionType;

public class FeedResponse {

    private UUID foodPostId;

    private String foodName;

    private String description;

    private BigDecimal rating;

    private BigDecimal price;

    private String thumbnailUrl;

    private UUID placeId;

    private String placeName;

    private UUID userId;

    private String userName;

    private String fullName;

    private String profileImageUrl;

    private Boolean verified;

    private Long confirmedCount;

    private Long notAccurateCount;

    private Long commentCount;

    private FoodReactionType myReaction;

    private LocalDateTime createdAt;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public UUID getPlaceId() {
		return placeId;
	}

	public void setPlaceId(UUID placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public Long getConfirmedCount() {
		return confirmedCount;
	}

	public void setConfirmedCount(Long confirmedCount) {
		this.confirmedCount = confirmedCount;
	}

	public Long getNotAccurateCount() {
		return notAccurateCount;
	}

	public void setNotAccurateCount(Long notAccurateCount) {
		this.notAccurateCount = notAccurateCount;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public FoodReactionType getMyReaction() {
		return myReaction;
	}

	public void setMyReaction(FoodReactionType myReaction) {
		this.myReaction = myReaction;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
