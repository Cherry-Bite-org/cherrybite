package com.cherrybite.AdminController;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cherrybite.payload.UpdatePlaceRequest;
import com.cherrybite.payload.response.ApiResponse;
import com.cherrybite.service.PlaceService;

@RestController
@RequestMapping("/admin/places")
public class AdminPlaceController {

  @Autowired
  private PlaceService placeService;

  @PutMapping("/{placeId}")
  public ResponseEntity<ApiResponse> updatePlace(@PathVariable UUID placeId,
      @RequestBody UpdatePlaceRequest request) {

    String message = placeService.updatePlace(placeId, request);

    ApiResponse response = new ApiResponse();
    response.setMessage(message);
    return ResponseEntity.ok(response);
  }

}
