package com.cherrybite.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cherrybite.payload.response.ApiResponse;
import com.cherrybite.payload.response.NotificationCountResponse;
import com.cherrybite.payload.response.NotificationResponse;
import com.cherrybite.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@GetMapping
	public ResponseEntity<Page<NotificationResponse>> getNotifications(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		return ResponseEntity.ok(notificationService.getNotifications(page, size));
	}

	@PutMapping("/{notificationId}/read")
	public ResponseEntity<ApiResponse> markAsRead(@PathVariable UUID notificationId) {
		String message = notificationService.markAsRead(notificationId);

		ApiResponse response = new ApiResponse();
		response.setMessage(message);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/read-all")
	public ResponseEntity<ApiResponse> markAllAsRead() {
		String message = notificationService.markAllAsRead();

		ApiResponse response = new ApiResponse();
		response.setMessage(message);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/unread-count")
	public ResponseEntity<NotificationCountResponse> getUnreadCount() {
		return ResponseEntity.ok(notificationService.getUnreadCount());
	}
}
