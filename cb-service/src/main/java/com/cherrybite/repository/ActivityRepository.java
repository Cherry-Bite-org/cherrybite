package com.cherrybite.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cherrybite.entity.Activity;
import com.cherrybite.entity.User;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {

  Page<Activity> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
