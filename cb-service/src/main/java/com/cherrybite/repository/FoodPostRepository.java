package com.cherrybite.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cherrybite.entity.FoodPost;
import com.cherrybite.entity.User;
import com.cherrybite.enums.FoodPostStatus;

public interface FoodPostRepository extends JpaRepository<FoodPost, UUID> {

  List<FoodPost> findByStatus(FoodPostStatus status);

  Optional<FoodPost> findByFoodPostIdAndStatus(UUID foodPostId, FoodPostStatus status);

  List<FoodPost> findByCreatedByAndStatusOrderByCreatedAtDesc(User user, FoodPostStatus status);

  List<FoodPost> findByStatusOrderByCreatedAtDesc(FoodPostStatus status);

  @Query("""
      SELECT fp
      FROM FoodPost fp
      WHERE fp.status='ACTIVE'
      ORDER BY
      CASE
      WHEN fp.createdBy IN (
      SELECT f.following
      FROM Follow f
      WHERE f.follower.userId=:userId
      )
      THEN 0
      ELSE 1
      END,
      fp.createdAt DESC
      """)
  Page<FoodPost> findFeed(@Param("userId") UUID userId, Pageable pageable);
}
