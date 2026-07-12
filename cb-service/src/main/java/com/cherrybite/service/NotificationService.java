package com.cherrybite.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.cherrybite.entity.Comment;
import com.cherrybite.entity.FoodPost;
import com.cherrybite.entity.User;
import com.cherrybite.enums.NotificationType;
import com.cherrybite.payload.response.NotificationCountResponse;
import com.cherrybite.payload.response.NotificationResponse;

public interface NotificationService {

	void createNotification(User receiver, User sender, FoodPost foodPost, Comment comment, NotificationType type);
	
	Page<NotificationResponse> getNotifications(int page, int size);
	
	String markAsRead(UUID notificationId);
	
	String markAllAsRead();
	
	NotificationCountResponse getUnreadCount();

}
