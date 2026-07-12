package com.cherrybite.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cherrybite.entity.FoodItem;
import com.cherrybite.entity.Place;
import com.cherrybite.entity.User;
import com.cherrybite.exception.UserException;
import com.cherrybite.payload.response.FoodSearchResult;
import com.cherrybite.payload.response.PlaceSearchResult;
import com.cherrybite.payload.response.SearchResponse;
import com.cherrybite.payload.response.UserSearchResult;
import com.cherrybite.repository.FoodItemRepository;
import com.cherrybite.repository.PlaceRepository;
import com.cherrybite.repository.UserRepository;
import com.cherrybite.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private FoodItemRepository foodItemRepository;

	@Override
	public SearchResponse search(String keyword) {

		if (keyword == null || keyword.isBlank()) {
			throw new UserException("Keyword is required");
		}

		keyword = keyword.trim();

		Pageable pageable = PageRequest.of(0, 10);

		List<FoodItem> foods = foodItemRepository.searchFoods(keyword, pageable);

		List<Place> places = placeRepository.searchPlaces(keyword, pageable);

		List<User> users = userRepository.searchUsers(keyword, pageable);

		SearchResponse response = new SearchResponse();

		response.setFoods(foods.stream().map(this::mapFood).toList());

		response.setPlaces(places.stream().map(this::mapPlace).toList());

		response.setUsers(users.stream().map(this::mapUser).toList());

		return response;
	}
	
	private FoodSearchResult mapFood(FoodItem foodItem) {

	    FoodSearchResult response = new FoodSearchResult();

	    response.setFoodItemId(foodItem.getFoodItemId());
	    response.setFoodName(foodItem.getFoodName());

	    response.setPlaceId(foodItem.getPlace().getPlaceId());
	    response.setPlaceName(foodItem.getPlace().getName());

	    // TODO: Calculate average from FoodPosts later
	    response.setAverageRating(0.0);

	    return response;
	}

	private PlaceSearchResult mapPlace(Place place) {

	    PlaceSearchResult response = new PlaceSearchResult();

	    response.setPlaceId(place.getPlaceId());
	    response.setName(place.getName());
	    response.setAddress(place.getAddress());

	    return response;
	}
	
	private UserSearchResult mapUser(User user) {

	    UserSearchResult response = new UserSearchResult();

	    response.setUserId(user.getUserId());
	    response.setUserName(user.getUserName());
	    response.setFullName(user.getFullName());
	    response.setProfileImageUrl(user.getProfileImageUrl());
	    response.setVerified(user.getIsVerified());

	    return response;
	}
}
