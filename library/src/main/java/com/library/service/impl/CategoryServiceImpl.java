package com.library.service.impl;

import com.github.pagehelper.PageHelper;
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
        return categoryDao.get(category);
    }

    @Override
    public PageInfo<Category> selectAll(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<Category> list=categoryDao.selectAll();
        //导航页码数,如果不传，PageInfo的默认值是8
        int navigatePages=3;
        PageInfo<Category> pageInfo=new PageInfo<>(list,navigatePages);
        System.out.println(pageInfo);
        return pageInfo;
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
