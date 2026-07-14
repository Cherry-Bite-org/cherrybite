package com.cherrybite.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherrybite.entity.FoodPost;
import com.cherrybite.entity.FoodPostReaction;
import com.cherrybite.entity.User;
import com.cherrybite.enums.ActivityType;
import com.cherrybite.enums.FoodPostStatus;
import com.cherrybite.enums.FoodReactionType;
import com.cherrybite.enums.NotificationType;
import com.cherrybite.exception.ResourceNotFoundException;
import com.cherrybite.exception.UserException;
import com.cherrybite.payload.response.FoodPostReactionSummaryResponse;
import com.cherrybite.repository.FoodPostReactionRepository;
import com.cherrybite.repository.FoodPostRepository;
import com.cherrybite.service.ActivityService;
import com.cherrybite.service.FoodPostReactionService;
import com.cherrybite.service.NotificationService;

@Service
public class FoodPostReactionServiceImpl implements FoodPostReactionService {

  private static final Logger log = LoggerFactory.getLogger(FoodPostReactionServiceImpl.class);

  @Autowired
  private FoodPostRepository foodPostRepository;

  @Autowired
  private UserServiceImpl userServiceImpl;

  @Autowired
  private FoodPostReactionRepository reactionRepository;

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private ActivityService activityService;

  @Override
  public String confirmFoodPost(UUID foodPostId) {

    User currentUser = userServiceImpl.getCurrentUserEntity();

    FoodPost foodPost = getActiveFoodPost(foodPostId);

    FoodPostReaction reaction =
        reactionRepository.findByFoodPostAndUser(foodPost, currentUser).orElse(null);

    if (reaction == null) {

      reaction = new FoodPostReaction();
      reaction.setFoodPost(foodPost);
      reaction.setUser(currentUser);
      reaction.setReactionType(FoodReactionType.CONFIRMED);

      reactionRepository.save(reaction);

      return "Food post confirmed";
    }

    if (reaction.getReactionType() == FoodReactionType.CONFIRMED) {
      throw new UserException("Already confirmed");
    }

    reaction.setReactionType(FoodReactionType.CONFIRMED);

    reactionRepository.save(reaction);

    activityService.createActivity(currentUser, foodPost, null, null, ActivityType.CONFIRMED);

    try {
      notificationService.createNotification(foodPost.getCreatedBy(), currentUser, foodPost, null,
          NotificationType.CONFIRMED);
    } catch (Exception e) {
      log.error("Failed to create Confirm notification", e);
    }
    return "Reaction updated successfully";
  }

  @Override
  public String markNotAccurate(UUID foodPostId) {

    User currentUser = userServiceImpl.getCurrentUserEntity();

    FoodPost foodPost = getActiveFoodPost(foodPostId);

    FoodPostReaction reaction =
        reactionRepository.findByFoodPostAndUser(foodPost, currentUser).orElse(null);

    if (reaction == null) {

      reaction = new FoodPostReaction();
      reaction.setFoodPost(foodPost);
      reaction.setUser(currentUser);
      reaction.setReactionType(FoodReactionType.NOT_ACCURATE);

      reactionRepository.save(reaction);

      return "Marked as not accurate";
    }

    if (reaction.getReactionType() == FoodReactionType.NOT_ACCURATE) {
      throw new UserException("Already marked as not accurate");
    }

    reaction.setReactionType(FoodReactionType.NOT_ACCURATE);

    reactionRepository.save(reaction);

    activityService.createActivity(currentUser, foodPost, null, null, ActivityType.NOT_ACCURATE);

    try {
      notificationService.createNotification(foodPost.getCreatedBy(), currentUser, foodPost, null,
          NotificationType.NOT_ACCURATE);
    } catch (Exception e) {
      log.error("Failed to create Not Accurate notification", e);
    }
    return "Reaction updated successfully";
  }

  @Override
  public String removeReaction(UUID foodPostId) {

    User currentUser = userServiceImpl.getCurrentUserEntity();

    FoodPost foodPost = getActiveFoodPost(foodPostId);

    FoodPostReaction reaction = reactionRepository.findByFoodPostAndUser(foodPost, currentUser)
        .orElseThrow(() -> new UserException("Reaction not found"));

    reactionRepository.delete(reaction);

    return "Reaction removed successfully";
  }

  @Override
  public FoodPostReactionSummaryResponse getReactionSummary(UUID foodPostId) {

    User currentUser = userServiceImpl.getCurrentUserEntity();

    FoodPost foodPost = getActiveFoodPost(foodPostId);

    long confirmed =
        reactionRepository.countByFoodPostAndReactionType(foodPost, FoodReactionType.CONFIRMED);

    long notAccurate =
        reactionRepository.countByFoodPostAndReactionType(foodPost, FoodReactionType.NOT_ACCURATE);

    FoodReactionType myReaction = reactionRepository.findByFoodPostAndUser(foodPost, currentUser)
        .map(FoodPostReaction::getReactionType).orElse(null);

    return new FoodPostReactionSummaryResponse(confirmed, notAccurate, myReaction);
  }

  private FoodPost getActiveFoodPost(UUID foodPostId) {
    return foodPostRepository.findByFoodPostIdAndStatus(foodPostId, FoodPostStatus.ACTIVE)
        .orElseThrow(() -> new ResourceNotFoundException("Food post not found"));
  }

}
