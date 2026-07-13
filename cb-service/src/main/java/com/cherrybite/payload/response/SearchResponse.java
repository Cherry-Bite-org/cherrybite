package com.cherrybite.payload.response;

import java.util.List;

public class SearchResponse {

  private List<FoodSearchResult> foods;

  private List<PlaceSearchResult> places;

  private List<UserSearchResult> users;

  public List<FoodSearchResult> getFoods() {
    return foods;
  }

  public void setFoods(List<FoodSearchResult> foods) {
    this.foods = foods;
  }

  public List<PlaceSearchResult> getPlaces() {
    return places;
  }

  public void setPlaces(List<PlaceSearchResult> places) {
    this.places = places;
  }

  public List<UserSearchResult> getUsers() {
    return users;
  }

  public void setUsers(List<UserSearchResult> users) {
    this.users = users;
  }

}
