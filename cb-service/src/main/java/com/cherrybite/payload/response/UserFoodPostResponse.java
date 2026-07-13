package com.cherrybite.payload.response;

import java.math.BigDecimal;
import java.util.UUID;

public class UserFoodPostResponse {

  private UUID foodPostId;

  private String foodName;

  private String thumbnailUrl;

  private BigDecimal rating;

  private Long likeCount;

  private Long commentCount;

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

  public String getThumbnailUrl() {
    return thumbnailUrl;
  }

  public void setThumbnailUrl(String thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }

  public BigDecimal getRating() {
    return rating;
  }

  public void setRating(BigDecimal rating) {
    this.rating = rating;
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

}
