package com.cherrybite.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cherrybite.payload.CreateFoodPostRequest;
import com.cherrybite.payload.response.ApiResponse;
import com.cherrybite.payload.response.CreateFoodPostResponse;
import com.cherrybite.service.FoodPostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/food-posts")
public class FoodPostController {

	@Autowired
	private FoodPostService foodPostService;

	@PostMapping
	public ResponseEntity<CreateFoodPostResponse> createFoodPost(@Valid @RequestBody CreateFoodPostRequest request) {
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

}
