package com.library.service;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Category;

import java.util.List;

public interface CategoryService {
    Category get(Category t);
    PageInfo<Category> selectAll(Integer currentPage,Integer pageSize);
    PageInfo<Category> selectAllByCondition(Integer currentPage,Integer pageSize,Category category);
    int save(Category t);
    int update(Category t);
    int delete(Integer id);

    void add(List<Category> list);
    public int addOne(Category category);
}
