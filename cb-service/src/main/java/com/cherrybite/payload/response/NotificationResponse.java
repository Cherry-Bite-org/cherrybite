package com.cherrybite.payload.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cherrybite.enums.NotificationType;

public class NotificationResponse {

  private UUID notificationId;

  private NotificationType type;

  private Boolean isRead;

  private LocalDateTime createdAt;

  private UUID senderId;

  private String senderUserName;

  private String senderFullName;

  private String senderProfileImageUrl;

  private UUID foodPostId;

  private UUID commentId;

  public UUID getNotificationId() {
    return notificationId;
  }

  public void setNotificationId(UUID notificationId) {
    this.notificationId = notificationId;
  }

  public NotificationType getType() {
    return type;
  }

  public void setType(NotificationType type) {
    this.type = type;
  }

  public Boolean getIsRead() {
    return isRead;
  }

  public void setIsRead(Boolean isRead) {
    this.isRead = isRead;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public UUID getSenderId() {
    return senderId;
  }

  public void setSenderId(UUID senderId) {
    this.senderId = senderId;
  }

  public String getSenderUserName() {
    return senderUserName;
  }

  public void setSenderUserName(String senderUserName) {
    this.senderUserName = senderUserName;
  }

  public String getSenderFullName() {
    return senderFullName;
  }

  public void setSenderFullName(String senderFullName) {
    this.senderFullName = senderFullName;
  }

  public String getSenderProfileImageUrl() {
    return senderProfileImageUrl;
  }

  public void setSenderProfileImageUrl(String senderProfileImageUrl) {
    this.senderProfileImageUrl = senderProfileImageUrl;
  }

  public UUID getFoodPostId() {
    return foodPostId;
  }

  public void setFoodPostId(UUID foodPostId) {
    this.foodPostId = foodPostId;
  }

  public UUID getCommentId() {
    return commentId;
  }

  public void setCommentId(UUID commentId) {
    this.commentId = commentId;
  }

}
