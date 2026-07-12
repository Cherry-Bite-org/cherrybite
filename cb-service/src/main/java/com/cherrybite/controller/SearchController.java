package com.cherrybite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cherrybite.payload.response.SearchResponse;
import com.cherrybite.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchService searchService;

	@GetMapping
	public ResponseEntity<SearchResponse> search(@RequestParam String keyword) {
		return ResponseEntity.ok(searchService.search(keyword));
	}

}
