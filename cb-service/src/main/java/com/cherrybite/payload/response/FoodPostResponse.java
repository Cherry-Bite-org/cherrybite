package com.cherrybite.payload.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class FoodPostResponse {

    private UUID foodPostId;

    private String foodName;

    private String description;

    private BigDecimal rating;

    private BigDecimal price;

    private Boolean recommended;

    private LocalDateTime createdAt;

    private CreatorResponse creator;

    private PlaceResponse place;

    private List<FoodPostImageResponse> images;

    private Long likeCount;

    private Long commentCount;

    private Boolean liked;

    private Boolean bookmarked;

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

	public Boolean getRecommended() {
		return recommended;
	}

	public void setRecommended(Boolean recommended) {
		this.recommended = recommended;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public CreatorResponse getCreator() {
		return creator;
	}

	public void setCreator(CreatorResponse creator) {
		this.creator = creator;
	}

	public PlaceResponse getPlace() {
		return place;
	}

	public void setPlace(PlaceResponse place) {
		this.place = place;
	}

	public List<FoodPostImageResponse> getImages() {
		return images;
	}

	public void setImages(List<FoodPostImageResponse> images) {
		this.images = images;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public Boolean getLiked() {
		return liked;
	}

	public void setLiked(Boolean liked) {
		this.liked = liked;
	}

	public Boolean getBookmarked() {
		return bookmarked;
	}

	public void setBookmarked(Boolean bookmarked) {
		this.bookmarked = bookmarked;
	}

}
