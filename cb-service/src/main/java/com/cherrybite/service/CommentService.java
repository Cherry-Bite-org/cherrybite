package com.cherrybite.service;

import java.util.List;
import java.util.UUID;

import com.cherrybite.payload.CreateCommentRequest;
import com.cherrybite.payload.response.CommentResponse;
import com.cherrybite.payload.response.CreateCommentResponse;

public interface CommentService {

	CreateCommentResponse addComment(UUID foodPostId, CreateCommentRequest request);

	CreateCommentResponse replyComment(UUID commentId, CreateCommentRequest request);

	String updateComment(UUID commentId, CreateCommentRequest request);
	
	String deleteComment(UUID commentId);
	
	List<CommentResponse> getComments(UUID foodPostId);
	
	String likeComment(UUID commentId);
	
	String unlikeComment(UUID commentId);
}
