package com.cherrybite.payload.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReplyResponse {

    private UUID commentId;

    private String comment;

    private LocalDateTime createdAt;

    private CommentUserResponse user;

    private Long likeCount;

	public UUID getCommentId() {
		return commentId;
	}

	public void setCommentId(UUID commentId) {
		this.commentId = commentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public CommentUserResponse getUser() {
		return user;
	}

	public void setUser(CommentUserResponse user) {
		this.user = user;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

}
