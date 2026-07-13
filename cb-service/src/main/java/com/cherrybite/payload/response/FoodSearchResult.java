package com.cherrybite.payload.response;

import java.util.UUID;

public class FoodSearchResult {

  private UUID foodItemId;

  private String foodName;

  private String placeName;

  private UUID placeId;

  private Double averageRating;

  public UUID getPlaceId() {
    return placeId;
  }

  public void setPlaceId(UUID placeId) {
    this.placeId = placeId;
  }

  public UUID getFoodItemId() {
    return foodItemId;
  }

  public void setFoodItemId(UUID foodItemId) {
    this.foodItemId = foodItemId;
  }

  public String getFoodName() {
    return foodName;
  }

  public void setFoodName(String foodName) {
    this.foodName = foodName;
  }

  public String getPlaceName() {
    return placeName;
  }

  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }

  public Double getAverageRating() {
    return averageRating;
  }

  public void setAverageRating(Double averageRating) {
    this.averageRating = averageRating;
  }
}
