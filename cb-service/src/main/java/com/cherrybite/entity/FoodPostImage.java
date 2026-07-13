package com.cherrybite.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "food_post_images")
public class FoodPostImage {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "image_id")
  private UUID imageId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food_post_id", nullable = false)
  private FoodPost foodPost;

  @Column(name = "image_url", nullable = false)
  private String imageUrl;

  @Column(name = "display_order")
  private Integer displayOrder;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  public UUID getImageId() {
    return imageId;
  }

  public void setImageId(UUID imageId) {
    this.imageId = imageId;
  }

  public FoodPost getFoodPost() {
    return foodPost;
  }

  public void setFoodPost(FoodPost foodPost) {
    this.foodPost = foodPost;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Integer getDisplayOrder() {
    return displayOrder;
  }

  public void setDisplayOrder(Integer displayOrder) {
    this.displayOrder = displayOrder;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

}
