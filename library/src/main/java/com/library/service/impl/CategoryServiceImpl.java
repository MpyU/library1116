package com.library.service.impl;

import com.library.pojo.Category;
import com.library.service.BookService;
import com.library.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl  implements CategoryService {
    @Override
    public Category get(Category category) {
        return null;
    }

    @Override
    public List<Category> selectAll() {
        return null;
    }

    @Override
    public int save(Category category) {
        return 0;
    }

    @Override
    public int update(Category category) {
        return 0;
    }

    @Override
    public int delete(Integer id) {
        return 0;
    }
}
