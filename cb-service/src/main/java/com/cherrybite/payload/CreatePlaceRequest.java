package com.cherrybite.payload;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatePlaceRequest {

  @NotBlank(message = "Place name is required")
  private String name;

  @NotBlank(message = "Address is required")
  private String address;

  @NotNull(message = "Latitude is required")
  private BigDecimal latitude;

  @NotNull(message = "Longitude is required")
  private BigDecimal longitude;

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

}
