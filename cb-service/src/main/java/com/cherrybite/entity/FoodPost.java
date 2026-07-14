package com.cherrybite.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cherrybite.enums.FoodPostStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "food_posts")
public class FoodPost {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "food_post_id")
  private UUID foodPostId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food_item_id", nullable = false)
  private FoodItem foodItem;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by", nullable = false)
  private User createdBy;

  @Column(name = "description", length = 1000)
  private String description;

  @Column(name = "rating", nullable = false)
  private BigDecimal rating;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "is_recommended")
  private Boolean recommended = false;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private FoodPostStatus status = FoodPostStatus.ACTIVE;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  public UUID getFoodPostId() {
    return foodPostId;
  }

  public void setFoodPostId(UUID foodPostId) {
    this.foodPostId = foodPostId;
  }

  public FoodItem getFoodItem() {
    return foodItem;
  }

  public void setFoodItem(FoodItem foodItem) {
    this.foodItem = foodItem;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
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

  public FoodPostStatus getStatus() {
    return status;
  }

  public void setStatus(FoodPostStatus status) {
    this.status = status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }

}
