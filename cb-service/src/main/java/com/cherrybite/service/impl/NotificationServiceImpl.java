package com.cherrybite.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cherrybite.entity.Comment;
import com.cherrybite.entity.FoodPost;
import com.cherrybite.entity.Notification;
import com.cherrybite.entity.User;
import com.cherrybite.enums.NotificationType;
import com.cherrybite.exception.ResourceNotFoundException;
import com.cherrybite.exception.UserException;
import com.cherrybite.payload.response.NotificationCountResponse;
import com.cherrybite.payload.response.NotificationResponse;
import com.cherrybite.repository.NotificationRepository;
import com.cherrybite.service.NotificationService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Override
	public void createNotification(User receiver, User sender, FoodPost foodPost, Comment comment,
			NotificationType type) {
       
		log.info("Create Notification Call");
		if (receiver.getUserId().equals(sender.getUserId())) {
			return;
		}

		Notification notification = new Notification();

		notification.setReceiver(receiver);
		notification.setSender(sender);
		notification.setFoodPost(foodPost);
		notification.setComment(comment);
		notification.setType(type);

		notificationRepository.save(notification);
		log.info("Notification Created for type : {}",type);
	}

	@Override
	public Page<NotificationResponse> getNotifications(int page, int size) {

		User currentUser = userServiceImpl.getCurrentUserEntity();

		Pageable pageable = PageRequest.of(page, size);

		Page<Notification> notifications = notificationRepository.findByReceiverOrderByCreatedAtDesc(currentUser,
				pageable);

		return notifications.map(this::mapNotificationResponse);
	}

	private NotificationResponse mapNotificationResponse(Notification notification) {

		NotificationResponse response = new NotificationResponse();

		response.setNotificationId(notification.getNotificationId());

		response.setType(notification.getType());

		response.setIsRead(notification.getIsRead());

		response.setCreatedAt(notification.getCreatedAt());

		response.setSenderId(notification.getSender().getUserId());

		response.setSenderUserName(notification.getSender().getUserName());

		response.setSenderFullName(notification.getSender().getFullName());

		response.setSenderProfileImageUrl(notification.getSender().getProfileImageUrl());

		if (notification.getFoodPost() != null) {
			response.setFoodPostId(notification.getFoodPost().getFoodPostId());
		}

		if (notification.getComment() != null) {
			response.setCommentId(notification.getComment().getCommentId());
		}

		return response;
	}

	@Override
	public String markAsRead(UUID notificationId) {

		User currentUser = userServiceImpl.getCurrentUserEntity();

		Notification notification = notificationRepository.findByNotificationIdAndReceiver(notificationId, currentUser)
				.orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

		if (Boolean.TRUE.equals(notification.getIsRead())) {
			throw new UserException("Notification already marked as read");
		}

		notification.setIsRead(true);

		notificationRepository.save(notification);

		return "Notification marked as read";
	}

	@Override
	@Transactional
	public String markAllAsRead() {
		User currentUser = userServiceImpl.getCurrentUserEntity();
		notificationRepository.markAllAsRead(currentUser);
		return "All notifications marked as read";
	}

	@Override
	public NotificationCountResponse getUnreadCount() {

		User currentUser = userServiceImpl.getCurrentUserEntity();

		long count = notificationRepository.countByReceiverAndIsReadFalse(currentUser);

		return new NotificationCountResponse(count);
	}
}
