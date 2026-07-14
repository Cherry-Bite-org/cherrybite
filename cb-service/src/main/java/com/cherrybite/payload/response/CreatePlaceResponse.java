package com.cherrybite.payload.response;

import java.util.UUID;

public class CreatePlaceResponse {

  private UUID placeId;

  private String message;

  public CreatePlaceResponse(UUID placeId, String message) {
    super();
    this.placeId = placeId;
    this.message = message;
  }

  public UUID getPlaceId() {
    return placeId;
  }

  public void setPlaceId(UUID placeId) {
    this.placeId = placeId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
