package com.library.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.pool.DruidDataSource;

@RestController
public class TestController {
	@RequestMapping("/test")
	public String test(int a, int b) {
		int i = 10 / 0;
		return null;

	}

	@Autowired
	private DataSource dataSource;

	@GetMapping("/dataSource")
	public void getDataSource() {

		System.out.println(((DruidDataSource) dataSource).getUrl());
	}

}
