package com.cherrybite.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/home")
public class HomeController {

	@GetMapping
    public String test() {
        return "JWT Security Working";
    }
}
