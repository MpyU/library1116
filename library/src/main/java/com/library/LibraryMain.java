package com.library;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
// @ImportResource("classpath:transaction.xml")
@MapperScan(basePackages = { "com.library.dao", "com.library.config" })
public class LibraryMain {
	public static void main(String[] args) {
		SpringApplication.run(LibraryMain.class, args);
	}
}
