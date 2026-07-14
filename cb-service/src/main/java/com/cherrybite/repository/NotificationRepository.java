package com.cherrybite.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import com.cherrybite.entity.Notification;
import com.cherrybite.entity.User;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

  Page<Notification> findByReceiverOrderByCreatedAtDesc(User receiver, Pageable pageable);

  long countByReceiverAndIsReadFalse(User receiver);

  Optional<Notification> findByNotificationIdAndReceiver(UUID notificationId, User receiver);

  @Modifying
  @Transactional
  @Query("""
      UPDATE Notification n
      SET n.isRead = true
      WHERE n.receiver = :receiver
      AND n.isRead = false
      """)
  int markAllAsRead(@Param("receiver") User receiver);
}
