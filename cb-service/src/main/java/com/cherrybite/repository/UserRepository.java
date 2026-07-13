package com.cherrybite.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cherrybite.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByEmail(String email);

  Optional<User> findByPhoneNumber(String phoneNumber);

  Optional<User> findByUserName(String userName);

  @Query("""
      SELECT u
      FROM User u
      WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%', :keyword, '%'))
         OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
      ORDER BY u.trustScore DESC
      """)
  List<User> searchUsers(@Param("keyword") String keyword, Pageable pageable);
}
