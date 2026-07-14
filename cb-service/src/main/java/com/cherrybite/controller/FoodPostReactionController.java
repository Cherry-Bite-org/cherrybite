package com.cherrybite.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cherrybite.payload.response.ApiResponse;
import com.cherrybite.payload.response.FoodPostReactionSummaryResponse;
import com.cherrybite.service.FoodPostReactionService;

@RestController
@RequestMapping("/food-posts-reaction")
public class FoodPostReactionController {

  @Autowired
  private FoodPostReactionService reactionService;

  @PostMapping("/{foodPostId}/confirm")
  public ResponseEntity<ApiResponse> confirmFoodPost(@PathVariable UUID foodPostId) {

    String message = reactionService.confirmFoodPost(foodPostId);

    ApiResponse response = new ApiResponse();
    response.setMessage(message);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/{foodPostId}/not-accurate")
  public ResponseEntity<ApiResponse> markNotAccurate(@PathVariable UUID foodPostId) {

    String message = reactionService.markNotAccurate(foodPostId);

    ApiResponse response = new ApiResponse();
    response.setMessage(message);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{foodPostId}/reaction")
  public ResponseEntity<ApiResponse> removeReaction(@PathVariable UUID foodPostId) {

    String message = reactionService.removeReaction(foodPostId);

    ApiResponse response = new ApiResponse();
    response.setMessage(message);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{foodPostId}/reactions")
  public ResponseEntity<FoodPostReactionSummaryResponse> getReactionSummary(
      @PathVariable UUID foodPostId) {
    return ResponseEntity.ok(reactionService.getReactionSummary(foodPostId));
  }
}
