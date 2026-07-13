package com.cherrybite.payload.response;

import java.util.UUID;

public class CreateCommentResponse {

  private UUID commentId;

  private String message;

  public CreateCommentResponse(UUID commentId, String message) {
    super();
    this.commentId = commentId;
    this.message = message;
  }

  public UUID getCommentId() {
    return commentId;
  }

  public void setCommentId(UUID commentId) {
    this.commentId = commentId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
