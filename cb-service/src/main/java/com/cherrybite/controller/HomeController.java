package com.cherrybite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cherrybite.payload.response.FeedResponse;
import com.cherrybite.service.FoodPostService;

@RestController
@RequestMapping("/home")
public class HomeController {

  @Autowired
  private FoodPostService foodPostService;

  @GetMapping("/feed")
  public ResponseEntity<Page<FeedResponse>> getFeed(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(foodPostService.getFeed(page, size));
  }
}
