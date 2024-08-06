package com.lbstspringboot3.controller;

import com.lbstspringboot3.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@Autowired
	private IUserService userService;

	@GetMapping("/hello")
	public String hello() {
		return "Hello World";
	}

	@GetMapping("/test")
	public String test() {
		return userService.getById(100000000).toString();
	}
}
