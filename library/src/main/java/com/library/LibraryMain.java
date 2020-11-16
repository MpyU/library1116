package com.library;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.library.dao")
public class LibraryMain {
    public static void main(String[] args) {
        SpringApplication.run(LibraryMain.class,args);
    }
}
