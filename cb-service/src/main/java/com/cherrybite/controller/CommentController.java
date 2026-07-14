package com.cherrybite.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cherrybite.payload.CreateCommentRequest;
import com.cherrybite.payload.response.ApiResponse;
import com.cherrybite.payload.response.CommentResponse;
import com.cherrybite.payload.response.CreateCommentResponse;
import com.cherrybite.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/comment")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @PostMapping("/{foodPostId}")
  public ResponseEntity<CreateCommentResponse> addComment(@PathVariable UUID foodPostId,
      @Valid @RequestBody CreateCommentRequest request) {

    return ResponseEntity.ok(commentService.addComment(foodPostId, request));
  }

  @PostMapping("/{commentId}/reply")
  public ResponseEntity<CreateCommentResponse> replyComment(@PathVariable UUID commentId,
      @Valid @RequestBody CreateCommentRequest request) {

    return ResponseEntity.ok(commentService.replyComment(commentId, request));
  }

  @PutMapping("/{commentId}")
  public ResponseEntity<ApiResponse> updateComment(@PathVariable UUID commentId,
      @Valid @RequestBody CreateCommentRequest request) {
    String message = commentService.updateComment(commentId, request);

    ApiResponse response = new ApiResponse();
    response.setMessage(message);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<ApiResponse> deleteComment(@PathVariable UUID commentId) {
    String message = commentService.deleteComment(commentId);

    ApiResponse response = new ApiResponse();
    response.setMessage(message);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{foodPostId}/comments")
  public ResponseEntity<List<CommentResponse>> getComments(@PathVariable UUID foodPostId) {
    return ResponseEntity.ok(commentService.getComments(foodPostId));
  }

  @PostMapping("/{commentId}/like")
  public ResponseEntity<ApiResponse> likeComment(@PathVariable UUID commentId) {

    String message = commentService.likeComment(commentId);

    ApiResponse response = new ApiResponse();
    response.setMessage(message);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{commentId}/like")
  public ResponseEntity<ApiResponse> unlikeComment(@PathVariable UUID commentId) {

    String message = commentService.unlikeComment(commentId);

    ApiResponse response = new ApiResponse();
    response.setMessage(message);
    return ResponseEntity.ok(response);
  }
}
