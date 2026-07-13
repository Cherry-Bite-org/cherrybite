package com.cherrybite.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.cherrybite.payload.CreatePlaceRequest;
import com.cherrybite.payload.UpdatePlaceRequest;
import com.cherrybite.payload.response.CreatePlaceResponse;
import com.cherrybite.payload.response.PlaceResponse;
import com.cherrybite.payload.response.PlaceSearchResponse;

public interface PlaceService {

  CreatePlaceResponse createPlace(CreatePlaceRequest request);

  List<PlaceSearchResponse> searchPlaces(String keyword, BigDecimal latitude, BigDecimal longitude);

  PlaceResponse getPlaceDetails(UUID placeId);

  List<PlaceSearchResponse> getNearbyPlaces(BigDecimal latitude, BigDecimal longitude,
      Double radius);

  String updatePlace(UUID placeId, UpdatePlaceRequest request);
}
