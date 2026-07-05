package com.cherrybite.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cherrybite.entity.FoodPost;

public interface FoodPostRepository extends JpaRepository<FoodPost, UUID>{

}
