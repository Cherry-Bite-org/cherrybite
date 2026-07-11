package com.cherrybite.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cherrybite.entity.Comment;
import com.cherrybite.entity.CommentLike;
import com.cherrybite.entity.User;

public interface CommentLikeRepository extends JpaRepository<CommentLike, UUID> {

	boolean existsByCommentAndUser(Comment comment, User user);

	Optional<CommentLike> findByCommentAndUser(Comment comment, User user);

	long countByComment(Comment comment);

}
