package com.cherrybite.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cherrybite.payload.CreateFoodPostRequest;
import com.cherrybite.payload.UpdateFoodPostRequest;
import com.cherrybite.payload.response.ApiResponse;
import com.cherrybite.payload.response.CreateFoodPostResponse;
import com.cherrybite.payload.response.FoodPostResponse;
import com.cherrybite.payload.response.NearbyFoodPostResponse;
import com.cherrybite.payload.response.UserFoodPostResponse;
import com.cherrybite.service.FoodPostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/food-posts")
public class FoodPostController {

  @Autowired
  private FoodPostService foodPostService;

  @PostMapping
  public ResponseEntity<CreateFoodPostResponse> createFoodPost(
      @Valid @RequestBody CreateFoodPostRequest request) {
    return ResponseEntity.ok(foodPostService.createFoodPost(request));
  }

  @PostMapping(value = "/{foodPostId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ApiResponse> uploadFoodImages(@PathVariable UUID foodPostId,
      @RequestParam("files") List<MultipartFile> files) {

    String message = foodPostService.uploadFoodImages(foodPostId, files);

    ApiResponse response = new ApiResponse();
    response.setMessage(message);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{foodPostId}")
  public ResponseEntity<FoodPostResponse> getFoodPost(@PathVariable UUID foodPostId) {
    return ResponseEntity.ok(foodPostService.getFoodPost(foodPostId));
  }

  @PutMapping("/{foodPostId}")
  public ResponseEntity<ApiResponse> updateFoodPost(@PathVariable UUID foodPostId,
      @Valid @RequestBody UpdateFoodPostRequest request) {
    String message = foodPostService.updateFoodPost(foodPostId, request);

    ApiResponse response = new ApiResponse();
    response.setMessage(message);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{foodPostId}")
  public ResponseEntity<ApiResponse> deleteFoodPost(@PathVariable UUID foodPostId) {
    String message = foodPostService.deleteFoodPost(foodPostId);

    ApiResponse response = new ApiResponse();
    response.setMessage(message);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/users/{username}")
  public ResponseEntity<List<UserFoodPostResponse>> getUserFoodPosts(
      @PathVariable String username) {
    return ResponseEntity.ok(foodPostService.getUserFoodPosts(username));
  }

  @GetMapping("/nearby")
  public ResponseEntity<List<NearbyFoodPostResponse>> getNearbyFoodPosts(
      @RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude,
      @RequestParam(defaultValue = "5") Double radius) {

    return ResponseEntity.ok(foodPostService.getNearbyFoodPosts(latitude, longitude, radius));
  }
}
