package com.cherrybite.service.impl;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cherrybite.entity.Place;
import com.cherrybite.entity.User;
import com.cherrybite.exception.ResourceNotFoundException;
import com.cherrybite.exception.UserException;
import com.cherrybite.payload.CreatePlaceRequest;
import com.cherrybite.payload.UpdatePlaceRequest;
import com.cherrybite.payload.response.CreatePlaceResponse;
import com.cherrybite.payload.response.PlaceResponse;
import com.cherrybite.payload.response.PlaceSearchResponse;
import com.cherrybite.repository.PlaceRepository;
import com.cherrybite.service.PlaceService;

@Service
public class PlaceServiceImpl implements PlaceService {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private PlaceRepository placeRepository;

	@Override
	public CreatePlaceResponse createPlace(CreatePlaceRequest request) {

		User currentUser = userServiceImpl.getCurrentUserEntity();

		validateCoordinates(request.getLatitude(), request.getLongitude());

		boolean exists = placeRepository.existsByNameIgnoreCaseAndAddressIgnoreCase(request.getName().trim(),
				request.getAddress().trim());

		if (exists) {
			throw new UserException("Place already exists");
		}

		Place place = new Place();

		place.setName(request.getName().trim());
		place.setAddress(request.getAddress().trim());
		place.setLatitude(request.getLatitude());
		place.setLongitude(request.getLongitude());

		place.setVerified(false);
		place.setActive(true);

		place.setCreatedBy(currentUser);

		Place savedPlace = placeRepository.save(place);

		return new CreatePlaceResponse(savedPlace.getPlaceId(), "Place created successfully");
	}

	private void validateCoordinates(BigDecimal latitude, BigDecimal longitude) {

		if (latitude.compareTo(BigDecimal.valueOf(-90)) < 0
				|| latitude.compareTo(BigDecimal.valueOf(90)) > 0) {

			throw new UserException("Invalid latitude");
		}

		if (longitude.compareTo(BigDecimal.valueOf(-180)) < 0
				|| longitude.compareTo(BigDecimal.valueOf(180)) > 0) {

			throw new UserException("Invalid longitude");
		}
	}

	@Override
	public List<PlaceSearchResponse> searchPlaces(String keyword, BigDecimal latitude, BigDecimal longitude) {

		if (keyword == null || keyword.isBlank()) {
			throw new UserException("Keyword is required");
		}

		keyword = keyword.trim();

		List<Place> places = placeRepository.searchPlaces(keyword, PageRequest.of(0, 20));

		return places.stream().map(place -> {

			PlaceSearchResponse response = new PlaceSearchResponse();

			response.setPlaceId(place.getPlaceId());
			response.setName(place.getName());
			response.setAddress(place.getAddress());

			if (latitude != null && longitude != null) {
				double distance = calculateDistance(latitude.doubleValue(), longitude.doubleValue(),
						place.getLatitude().doubleValue(), place.getLongitude().doubleValue());

				response.setDistance(formatDistance(distance));
			} else {
				response.setDistance(null);
			}
			return response;
		}).toList();
	}

	private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

		final int EARTH_RADIUS = 6371;

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);

		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS * c;
	}

	private String formatDistance(double distance) {
		if (distance < 1) {
			return Math.round(distance * 1000) + " m";
		}
		return String.format("%.1f km", distance);
	}

	@Override
	public PlaceResponse getPlaceDetails(UUID placeId) {

		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new ResourceNotFoundException("Place not found"));

		PlaceResponse response = new PlaceResponse();

		response.setPlaceId(place.getPlaceId());
		response.setName(place.getName());
		response.setAddress(place.getAddress());
		response.setLatitude(place.getLatitude());
		response.setLongitude(place.getLongitude());
		response.setVerified(place.getVerified());
		response.setPlaceImageUrl(place.getPlaceImageUrl());

		// Temporary values until Food Post module is completed
		response.setAverageRating(0.0);
		response.setFoodPostCount(0L);
		response.setReviewCount(0L);
		response.setPhotoCount(0L);

		return response;
	}

	@Override
	public List<PlaceSearchResponse> getNearbyPlaces(BigDecimal latitude, BigDecimal longitude, Double radius) {

		List<Place> places = placeRepository.findAllActivePlaces();

		return places.stream().map(place -> {

			double distance = calculateDistance(latitude.doubleValue(), longitude.doubleValue(),
					place.getLatitude().doubleValue(), place.getLongitude().doubleValue());

			if (distance > radius) {
				return null;
			}

			PlaceSearchResponse response = new PlaceSearchResponse();

			response.setPlaceId(place.getPlaceId());
			response.setName(place.getName());
			response.setAddress(place.getAddress());
			response.setDistance(formatDistance(distance));

			return response;

		}).filter(Objects::nonNull)
				.sorted(Comparator.comparing(p -> Double.parseDouble(p.getDistance().replace(" km", "")))).toList();
	}

	@Override
	public String updatePlace(UUID placeId, UpdatePlaceRequest request) {

		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new ResourceNotFoundException("Place not found"));
		
		validateCoordinates(request.getLatitude(), request.getLongitude());

		if (request.getName() != null && !request.getName().isBlank()) {
			place.setName(request.getName().trim());
		}

		if (request.getAddress() != null && !request.getAddress().isBlank()) {
			place.setAddress(request.getAddress().trim());
		}

		if (request.getLatitude() != null) {
			place.setLatitude(request.getLatitude());
		}

		if (request.getLongitude() != null) {
			place.setLongitude(request.getLongitude());
		}

		if (request.getVerified() != null) {
			place.setVerified(request.getVerified());
		}

		placeRepository.save(place);

		return "Place updated successfully";
	}
}
