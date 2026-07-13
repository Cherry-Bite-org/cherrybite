package com.cherrybite.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cherrybite.entity.FoodPost;
import com.cherrybite.entity.FoodPostImage;

public interface FoodPostImageRepository extends JpaRepository<FoodPostImage, UUID> {

  List<FoodPostImage> findByFoodPostOrderByDisplayOrderAsc(FoodPost foodPost);

  @Query("""
      SELECT COALESCE(MAX(i.displayOrder), 0)
      FROM FoodPostImage i
      WHERE i.foodPost = :foodPost
      """)
  Integer findMaxDisplayOrder(@Param("foodPost") FoodPost foodPost);

  Optional<FoodPostImage> findFirstByFoodPostOrderByDisplayOrderAsc(FoodPost foodPost);
}
