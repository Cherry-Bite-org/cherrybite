package com.cherrybite.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cherrybite.entity.Follow;
import com.cherrybite.entity.User;

public interface FollowRepository extends JpaRepository<Follow, UUID> {

	@Query("""
			    SELECT f.following.userId
			    FROM Follow f
			    WHERE f.follower.userId = :userId
			""")
	List<UUID> findFollowingIds(UUID userId);

	boolean existsByFollowerAndFollowing(User follower, User following);
	
	Optional<Follow> findByFollowerAndFollowing(User follower, User following);
}
