package com.cherrybite.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "food_items",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"place_id", "food_name"})})
public class FoodItem {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "food_item_id")
  private UUID foodItemId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "place_id", nullable = false)
  private Place place;

  @Column(name = "food_name", nullable = false, length = 150)
  private String foodName;

  @Column(name = "category", length = 100)
  private String category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by", nullable = false)
  private User createdBy;

  @Column(name = "is_active")
  private Boolean active = true;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  public UUID getFoodItemId() {
    return foodItemId;
  }

  public void setFoodItemId(UUID foodItemId) {
    this.foodItemId = foodItemId;
  }

  public Place getPlace() {
    return place;
  }

  public void setPlace(Place place) {
    this.place = place;
  }

  public String getFoodName() {
    return foodName;
  }

  public void setFoodName(String foodName) {
    this.foodName = foodName;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

}
