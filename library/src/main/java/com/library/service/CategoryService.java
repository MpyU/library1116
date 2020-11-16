package com.library.service;

import com.library.pojo.Book;
import com.library.pojo.Category;

import java.util.List;

public interface CategoryService {
    Category get(Category t);
    List<Category> selectAll();
    int save(Category t);
    int update(Category t);
    int delete(Integer id);
}
