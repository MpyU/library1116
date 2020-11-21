package com.library.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@RequestMapping("/test")
	public String test(int a, int b) {
		int i = 10 / 0;
		return null;

	}

}
