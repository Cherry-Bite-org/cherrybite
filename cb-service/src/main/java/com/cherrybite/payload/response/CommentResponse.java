package com.cherrybite.payload.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CommentResponse {

    private UUID commentId;

    private String comment;

    private LocalDateTime createdAt;

    private CommentUserResponse user;

    private Long likeCount;

    private Long replyCount;

    private List<ReplyResponse> replies;

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

	public Long getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Long replyCount) {
		this.replyCount = replyCount;
	}

	public List<ReplyResponse> getReplies() {
		return replies;
	}

	public void setReplies(List<ReplyResponse> replies) {
		this.replies = replies;
	}

}
