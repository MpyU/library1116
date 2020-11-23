package com.library.dao;

import com.library.pojo.Category;

import java.util.List;

public interface CategoryDao extends BaseDao<Category> {
    List<Category> selectAllByCondition(Category category);

    void add(List<Category> list);

    int addOne(Category category);
}
