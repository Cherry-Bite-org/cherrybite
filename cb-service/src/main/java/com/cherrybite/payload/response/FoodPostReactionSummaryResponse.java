package com.cherrybite.payload.response;

import com.cherrybite.enums.FoodReactionType;

public class FoodPostReactionSummaryResponse {

    private Long confirmedCount;

    private Long notAccurateCount;

    private FoodReactionType myReaction;

	public FoodPostReactionSummaryResponse(Long confirmedCount, Long notAccurateCount, FoodReactionType myReaction) {
		super();
		this.confirmedCount = confirmedCount;
		this.notAccurateCount = notAccurateCount;
		this.myReaction = myReaction;
	}

	public Long getConfirmedCount() {
		return confirmedCount;
	}

	public void setConfirmedCount(Long confirmedCount) {
		this.confirmedCount = confirmedCount;
	}

	public Long getNotAccurateCount() {
		return notAccurateCount;
	}

	public void setNotAccurateCount(Long notAccurateCount) {
		this.notAccurateCount = notAccurateCount;
	}

	public FoodReactionType getMyReaction() {
		return myReaction;
	}

	public void setMyReaction(FoodReactionType myReaction) {
		this.myReaction = myReaction;
	}

}
