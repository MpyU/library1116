package com.library.service.impl;

import com.github.pagehelper.PageInfo;
import com.library.dao.CategoryDao;
import com.library.pojo.Category;
import com.library.service.BookService;
import com.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl  implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Category get(Category category) {
        return null;
    }

    @Override
    public PageInfo<Category> selectAll(Integer currentPage, Integer pageSize) {
        return null;
    }

    @Override
    public PageInfo<Category> selectAllByCondition(Integer currentPage, Integer pageSize, Category category) {
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

    @Override
    public void add(List<Category> list) {
        categoryDao.add(list);
    }
}
