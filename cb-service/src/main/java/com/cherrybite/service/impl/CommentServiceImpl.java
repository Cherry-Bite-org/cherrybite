package com.cherrybite.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherrybite.entity.Comment;
import com.cherrybite.entity.CommentLike;
import com.cherrybite.entity.FoodPost;
import com.cherrybite.entity.User;
import com.cherrybite.enums.ActivityType;
import com.cherrybite.enums.CommentStatus;
import com.cherrybite.enums.FoodPostStatus;
import com.cherrybite.enums.NotificationType;
import com.cherrybite.exception.ResourceNotFoundException;
import com.cherrybite.exception.UserException;
import com.cherrybite.payload.CreateCommentRequest;
import com.cherrybite.payload.response.CommentResponse;
import com.cherrybite.payload.response.CommentUserResponse;
import com.cherrybite.payload.response.CreateCommentResponse;
import com.cherrybite.payload.response.ReplyResponse;
import com.cherrybite.repository.CommentLikeRepository;
import com.cherrybite.repository.CommentRepository;
import com.cherrybite.repository.FoodPostRepository;
import com.cherrybite.service.ActivityService;
import com.cherrybite.service.CommentService;
import com.cherrybite.service.NotificationService;

@Service
public class CommentServiceImpl implements CommentService {

  private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

  @Autowired
  private UserServiceImpl userServiceImpl;

  @Autowired
  private FoodPostRepository foodPostRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private CommentLikeRepository commentLikeRepository;

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private ActivityService activityService;

  @Override
  public CreateCommentResponse addComment(UUID foodPostId, CreateCommentRequest request) {

    User currentUser = userServiceImpl.getCurrentUserEntity();

    FoodPost foodPost =
        foodPostRepository.findByFoodPostIdAndStatus(foodPostId, FoodPostStatus.ACTIVE)
            .orElseThrow(() -> new ResourceNotFoundException("Food post not found"));

    String commentText = request.getComment().trim();

    if (commentText.isBlank()) {
      throw new UserException("Comment cannot be empty");
    }

    Comment comment = new Comment();

    comment.setFoodPost(foodPost);
    comment.setUser(currentUser);
    comment.setComment(commentText);

    Comment savedComment = commentRepository.save(comment);

    activityService.createActivity(currentUser, foodPost, savedComment, null, ActivityType.COMMENT);

    try {
      notificationService.createNotification(foodPost.getCreatedBy(), currentUser, foodPost,
          comment, NotificationType.COMMENT);
    } catch (Exception e) {
      log.error("Failed to create Comment notification", e);
    }
    return new CreateCommentResponse(savedComment.getCommentId(), "Comment added successfully");
  }

  private Comment getActiveComment(UUID commentId) {
    return commentRepository.findByCommentIdAndStatus(commentId, CommentStatus.ACTIVE)
        .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
  }

  @Override
  public CreateCommentResponse replyComment(UUID commentId, CreateCommentRequest request) {

    User currentUser = userServiceImpl.getCurrentUserEntity();

    Comment parentComment = getActiveComment(commentId);

    // Optional: Don't allow replies to deleted food posts
    if (parentComment.getFoodPost().getStatus() != FoodPostStatus.ACTIVE) {
      throw new UserException("Food post not found");
    }

    String commentText = request.getComment().trim();

    if (commentText.isBlank()) {
      throw new UserException("Reply cannot be empty");
    }

    Comment reply = new Comment();

    reply.setFoodPost(parentComment.getFoodPost());

    reply.setUser(currentUser);

    reply.setParentComment(parentComment);

    reply.setComment(commentText);

    Comment savedReply = commentRepository.save(reply);

    // Update reply count
    parentComment.setReplyCount(parentComment.getReplyCount() + 1);
    commentRepository.save(parentComment);

    activityService.createActivity(currentUser, parentComment.getFoodPost(), savedReply, null,
        ActivityType.REPLY);

    try {
      notificationService.createNotification(parentComment.getUser(), currentUser,
          parentComment.getFoodPost(), reply, NotificationType.REPLY);
    } catch (Exception e) {
      log.error("Failed to create reply comment notification", e);
    }
    return new CreateCommentResponse(savedReply.getCommentId(), "Reply added successfully");
  }

  @Override
  public String updateComment(UUID commentId, CreateCommentRequest request) {

    User currentUser = userServiceImpl.getCurrentUserEntity();

    Comment comment = getActiveComment(commentId);

    if (!comment.getUser().getUserId().equals(currentUser.getUserId())) {

      throw new UserException("You are not allowed to update this comment");
    }

    if (comment.getFoodPost().getStatus() != FoodPostStatus.ACTIVE) {
      throw new UserException("Food post not found");
    }

    String commentText = request.getComment().trim();

    if (commentText.isBlank()) {
      throw new UserException("Comment cannot be empty");
    }

    comment.setComment(commentText);

    commentRepository.save(comment);

    return "Comment updated successfully";
  }

  @Override
  public String deleteComment(UUID commentId) {

    User currentUser = userServiceImpl.getCurrentUserEntity();

    Comment comment = getActiveComment(commentId);

    if (!comment.getUser().getUserId().equals(currentUser.getUserId())) {

      throw new UserException("You are not allowed to delete this comment");
    }

    comment.setStatus(CommentStatus.DELETED);
    comment.setComment("This comment has been deleted");

    commentRepository.save(comment);

    return "Comment deleted successfully";
  }

  @Override
  public List<CommentResponse> getComments(UUID foodPostId) {

    FoodPost foodPost =
        foodPostRepository.findByFoodPostIdAndStatus(foodPostId, FoodPostStatus.ACTIVE)
            .orElseThrow(() -> new ResourceNotFoundException("Food post not found"));

    List<Comment> comments =
        commentRepository.findByFoodPostAndParentCommentIsNullAndStatusOrderByCreatedAtDesc(
            foodPost, CommentStatus.ACTIVE);

    return comments.stream().map(this::mapCommentResponse).toList();
  }

  private CommentResponse mapCommentResponse(Comment comment) {

    CommentResponse response = new CommentResponse();

    response.setCommentId(comment.getCommentId());
    response.setComment(comment.getComment());
    response.setCreatedAt(comment.getCreatedAt());

    CommentUserResponse user = new CommentUserResponse();

    user.setUserId(comment.getUser().getUserId());
    user.setUserName(comment.getUser().getUserName());
    user.setFullName(comment.getUser().getFullName());
    user.setProfileImageUrl(comment.getUser().getProfileImageUrl());
    user.setVerified(comment.getUser().getIsVerified());

    response.setUser(user);

    response.setLikeCount(comment.getLikeCount());
    response.setReplyCount(comment.getReplyCount());

    List<ReplyResponse> replies = commentRepository
        .findByParentCommentAndStatusOrderByCreatedAtAsc(comment, CommentStatus.ACTIVE).stream()
        .map(this::mapReplyResponse).toList();

    response.setReplies(replies);

    return response;
  }

  private ReplyResponse mapReplyResponse(Comment reply) {

    ReplyResponse response = new ReplyResponse();

    response.setCommentId(reply.getCommentId());
    response.setComment(reply.getComment());
    response.setCreatedAt(reply.getCreatedAt());

    CommentUserResponse user = new CommentUserResponse();

    user.setUserId(reply.getUser().getUserId());
    user.setUserName(reply.getUser().getUserName());
    user.setFullName(reply.getUser().getFullName());
    user.setProfileImageUrl(reply.getUser().getProfileImageUrl());
    user.setVerified(reply.getUser().getIsVerified());

    response.setUser(user);

    response.setLikeCount(reply.getLikeCount());

    return response;
  }

  @Override
  public String likeComment(UUID commentId) {

    User currentUser = userServiceImpl.getCurrentUserEntity();

    Comment comment = getActiveComment(commentId);

    if (commentLikeRepository.existsByCommentAndUser(comment, currentUser)) {
      throw new UserException("Comment already liked");
    }

    CommentLike like = new CommentLike();

    like.setComment(comment);
    like.setUser(currentUser);

    commentLikeRepository.save(like);

    comment.setLikeCount(comment.getLikeCount() + 1);
    commentRepository.save(comment);

    return "Comment liked successfully";
  }

  @Override
  public String unlikeComment(UUID commentId) {

    User currentUser = userServiceImpl.getCurrentUserEntity();

    Comment comment = getActiveComment(commentId);

    CommentLike like = commentLikeRepository.findByCommentAndUser(comment, currentUser)
        .orElseThrow(() -> new UserException("Comment is not liked"));

    commentLikeRepository.delete(like);

    comment.setLikeCount(Math.max(0, comment.getLikeCount() - 1));

    commentRepository.save(comment);

    return "Comment unliked successfully";
  }

}
