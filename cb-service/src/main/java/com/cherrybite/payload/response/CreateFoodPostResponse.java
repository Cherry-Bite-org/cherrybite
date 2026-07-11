package com.cherrybite.payload.response;

import java.util.UUID;

public class CreateFoodPostResponse {

    private UUID foodPostId;

    private String message;

	public CreateFoodPostResponse(UUID foodPostId, String message) {
		super();
		this.foodPostId = foodPostId;
		this.message = message;
	}

	public UUID getFoodPostId() {
		return foodPostId;
	}

	public void setFoodPostId(UUID foodPostId) {
		this.foodPostId = foodPostId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
