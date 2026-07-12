package com.cherrybite.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cherrybite.entity.FoodItem;
import com.cherrybite.entity.Place;

public interface FoodItemRepository extends JpaRepository<FoodItem, UUID> {

	Optional<FoodItem> findByPlaceAndFoodNameIgnoreCase(Place place, String foodName);

	@Query("""
			SELECT f
			FROM FoodItem f
			WHERE LOWER(f.foodName)
			LIKE LOWER(CONCAT('%', :keyword, '%'))
			ORDER BY f.foodName
			""")
	List<FoodItem> searchFoods(@Param("keyword") String keyword, Pageable pageable);
}
