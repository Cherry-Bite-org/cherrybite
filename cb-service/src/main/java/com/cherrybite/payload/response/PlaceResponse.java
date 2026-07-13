package com.cherrybite.payload.response;

import java.math.BigDecimal;
import java.util.UUID;

public class PlaceResponse {

  private UUID placeId;

  private String name;

  private String address;

  private BigDecimal latitude;

  private BigDecimal longitude;

  private Boolean verified;

  private String placeImageUrl;

  private Double averageRating;

  private Long foodPostCount;

  private Long reviewCount;

  private Long photoCount;

  public UUID getPlaceId() {
    return placeId;
  }

  public void setPlaceId(UUID placeId) {
    this.placeId = placeId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public BigDecimal getLatitude() {
    return latitude;
  }

  public void setLatitude(BigDecimal latitude) {
    this.latitude = latitude;
  }

  public BigDecimal getLongitude() {
    return longitude;
  }

  public void setLongitude(BigDecimal longitude) {
    this.longitude = longitude;
  }

  public Boolean getVerified() {
    return verified;
  }

  public void setVerified(Boolean verified) {
    this.verified = verified;
  }

  public String getPlaceImageUrl() {
    return placeImageUrl;
  }

  public void setPlaceImageUrl(String placeImageUrl) {
    this.placeImageUrl = placeImageUrl;
  }

  public Double getAverageRating() {
    return averageRating;
  }

  public void setAverageRating(Double averageRating) {
    this.averageRating = averageRating;
  }

  public Long getFoodPostCount() {
    return foodPostCount;
  }

  public void setFoodPostCount(Long foodPostCount) {
    this.foodPostCount = foodPostCount;
  }

  public Long getReviewCount() {
    return reviewCount;
  }

  public void setReviewCount(Long reviewCount) {
    this.reviewCount = reviewCount;
  }

  public Long getPhotoCount() {
    return photoCount;
  }

  public void setPhotoCount(Long photoCount) {
    this.photoCount = photoCount;
  }

}
