package com.cherrybite.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cherrybite.entity.FoodItem;
import com.cherrybite.entity.Place;

public interface FoodItemRepository extends JpaRepository<FoodItem, UUID>{

	 Optional<FoodItem> findByPlaceAndFoodNameIgnoreCase(
	            Place place,
	            String foodName);
}
