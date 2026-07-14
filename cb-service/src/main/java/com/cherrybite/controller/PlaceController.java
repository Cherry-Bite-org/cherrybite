package com.cherrybite.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cherrybite.payload.CreatePlaceRequest;
import com.cherrybite.payload.response.CreatePlaceResponse;
import com.cherrybite.payload.response.PlaceResponse;
import com.cherrybite.payload.response.PlaceSearchResponse;
import com.cherrybite.service.PlaceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/places")
public class PlaceController {

  @Autowired
  private PlaceService placeService;

  @PostMapping
  public ResponseEntity<CreatePlaceResponse> createPlace(
      @Valid @RequestBody CreatePlaceRequest request) {
    return ResponseEntity.ok(placeService.createPlace(request));
  }

  @GetMapping("/search")
  public ResponseEntity<List<PlaceSearchResponse>> searchPlaces(@RequestParam String keyword,
      @RequestParam(required = false) BigDecimal latitude,
      @RequestParam(required = false) BigDecimal longitude) {
    return ResponseEntity.ok(placeService.searchPlaces(keyword, latitude, longitude));
  }

  @GetMapping("/{placeId}")
  public ResponseEntity<PlaceResponse> getPlaceDetails(@PathVariable UUID placeId) {
    return ResponseEntity.ok(placeService.getPlaceDetails(placeId));
  }

  @GetMapping("/nearby")
  public ResponseEntity<List<PlaceSearchResponse>> getNearbyPlaces(
      @RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude,
      @RequestParam(defaultValue = "5") Double radius) {

    return ResponseEntity.ok(placeService.getNearbyPlaces(latitude, longitude, radius));
  }

}
