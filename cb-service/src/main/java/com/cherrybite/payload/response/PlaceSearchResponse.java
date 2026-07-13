package com.cherrybite.payload.response;

import java.util.UUID;

public class PlaceSearchResponse {

  private UUID placeId;

  private String name;

  private String address;

  private String distance;

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

  public String getDistance() {
    return distance;
  }

  public void setDistance(String distance) {
    this.distance = distance;
  }

}
