package com.cherrybite.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cherrybite.entity.FoodPost;
import com.cherrybite.entity.FoodPostReaction;
import com.cherrybite.entity.User;
import com.cherrybite.enums.FoodReactionType;

public interface FoodPostReactionRepository extends JpaRepository<FoodPostReaction, UUID> {

  Optional<FoodPostReaction> findByFoodPostAndUser(FoodPost foodPost, User user);

  boolean existsByFoodPostAndUser(FoodPost foodPost, User user);

  long countByFoodPostAndReactionType(FoodPost foodPost, FoodReactionType reactionType);

}
