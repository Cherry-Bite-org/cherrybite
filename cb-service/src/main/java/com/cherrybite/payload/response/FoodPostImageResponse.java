package com.cherrybite.payload.response;

import java.util.UUID;

public class FoodPostImageResponse {
	
	 private UUID imageId;

	 private String imageUrl;

	 public FoodPostImageResponse(UUID imageId, String imageUrl) {
		super();
		this.imageId = imageId;
		this.imageUrl = imageUrl;
	}

	 public UUID getImageId() {
		 return imageId;
	 }

	 public void setImageId(UUID imageId) {
		 this.imageId = imageId;
	 }

	 public String getImageUrl() {
		 return imageUrl;
	 }

	 public void setImageUrl(String imageUrl) {
		 this.imageUrl = imageUrl;
	 }

}
