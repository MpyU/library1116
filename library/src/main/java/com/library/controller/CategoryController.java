package com.library.controller;

import com.library.climb.ClimbData;
import com.library.pojo.Category;
import com.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/add")
    public String lists(){

        List<Category> list = ClimbData.categories();
        categoryService.add(list);

        return "success";
    }
}
