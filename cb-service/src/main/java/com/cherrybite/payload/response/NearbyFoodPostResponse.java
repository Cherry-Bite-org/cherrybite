package com.cherrybite.payload.response;

import java.math.BigDecimal;
import java.util.UUID;

public class NearbyFoodPostResponse {

    private UUID foodPostId;

    private String foodName;

    private BigDecimal rating;

    private BigDecimal price;

    private String thumbnailUrl;

    private String distance;

    private UUID placeId;

    private String placeName;

    private UUID userId;

    private String userName;

    private String profileImageUrl;

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

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
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

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

}