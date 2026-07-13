package com.cherrybite.payload;

import java.math.BigDecimal;

public class UpdateLocationRequest {

  private BigDecimal lastLatitude;

  private BigDecimal lastLongitude;

  public BigDecimal getLastLatitude() {
    return lastLatitude;
  }

  public void setLastLatitude(BigDecimal lastLatitude) {
    this.lastLatitude = lastLatitude;
  }

  public BigDecimal getLastLongitude() {
    return lastLongitude;
  }

  public void setLastLongitude(BigDecimal lastLongitude) {
    this.lastLongitude = lastLongitude;
  }
}
