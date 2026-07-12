package com.cherrybite.service;

import java.util.UUID;

import com.cherrybite.payload.response.FoodPostReactionSummaryResponse;

public interface FoodPostReactionService {

    String confirmFoodPost(UUID foodPostId);

    String markNotAccurate(UUID foodPostId);

    String removeReaction(UUID foodPostId);

    FoodPostReactionSummaryResponse getReactionSummary(UUID foodPostId);

}
