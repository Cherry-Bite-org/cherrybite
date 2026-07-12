package com.cherrybite.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cherrybite.entity.Comment;
import com.cherrybite.entity.FoodPost;
import com.cherrybite.enums.CommentStatus;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

	List<Comment> findByFoodPostAndParentCommentIsNullAndStatusOrderByCreatedAtDesc(FoodPost foodPost,
			CommentStatus status);

	List<Comment> findByParentCommentAndStatusOrderByCreatedAtAsc(Comment parentComment, CommentStatus status);

	Optional<Comment> findByCommentIdAndStatus(UUID commentId, CommentStatus status);

	long countByFoodPostAndStatus(FoodPost foodPost, CommentStatus status);
}
