package com.cherrybite.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cherrybite.entity.FoodPost;
import com.cherrybite.entity.User;
import com.cherrybite.enums.FoodPostStatus;

public interface FoodPostRepository extends JpaRepository<FoodPost, UUID> {

	List<FoodPost> findByStatus(FoodPostStatus status);

	Optional<FoodPost> findByFoodPostIdAndStatus(UUID foodPostId, FoodPostStatus status);

	List<FoodPost> findByCreatedByAndStatusOrderByCreatedAtDesc(User user, FoodPostStatus status);

	List<FoodPost> findByStatusOrderByCreatedAtDesc(FoodPostStatus status);
}
