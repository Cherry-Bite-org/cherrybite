package com.cherrybite.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.cherrybite.enums.ActivityType;

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
@Table(name = "activities")
public class Activity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "activity_id")
  private UUID activityId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food_post_id")
  private FoodPost foodPost;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "comment_id")
  private Comment comment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "follow_user_id")
  private User followUser;

  @Enumerated(EnumType.STRING)
  @Column(name = "activity_type")
  private ActivityType activityType;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  public UUID getActivityId() {
    return activityId;
  }

  public void setActivityId(UUID activityId) {
    this.activityId = activityId;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public FoodPost getFoodPost() {
    return foodPost;
  }

  public void setFoodPost(FoodPost foodPost) {
    this.foodPost = foodPost;
  }

  public Comment getComment() {
    return comment;
  }

  public void setComment(Comment comment) {
    this.comment = comment;
  }

  public User getFollowUser() {
    return followUser;
  }

  public void setFollowUser(User followUser) {
    this.followUser = followUser;
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
}
