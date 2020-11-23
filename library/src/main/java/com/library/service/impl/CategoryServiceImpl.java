package com.library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.dao.BookDao;
import com.library.dao.CategoryDao;
import com.library.pojo.Book;
import com.library.pojo.Category;
import com.library.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	BookDao bookDao;

	@Override
	public Category get(Category category) {
		return categoryDao.get(category);
	}

	@Override
	public PageInfo<Category> selectAll(Integer currentPage, Integer pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<Category> list = categoryDao.selectAll();
		// 导航页码数,如果不传，PageInfo的默认值是8
		int navigatePages = 3;
		PageInfo<Category> pageInfo = new PageInfo<>(list, navigatePages);
		System.out.println(pageInfo);
		return pageInfo;
	}

	@Override
	public PageInfo<Category> selectAllByCondition(Integer currentPage, Integer pageSize, Category category) {

		if (category.getCategoryName() != null) {
			category.setCategoryName("%" + category.getCategoryName() + "%");
		}
		PageHelper.startPage(currentPage, pageSize);
		List<Category> list = categoryDao.selectAllByCondition(category);
		PageInfo<Category> pageInfo = new PageInfo<>(list);
		return pageInfo;

	}

	@Override
	public int save(Category category) {

		return categoryDao.save(category);
	}

	@Override
	public int update(Category category) {

		return categoryDao.update(category);
	}

	@Override
	public int delete(Integer id) {
		if (id != null) {
			Book book = new Book();
			book.setCid(id);
			List<Book> list = bookDao.selectAllByCondition(book);

			for (Book book2 : list) {
				System.err.println(book2);
				// 把该分类的书id设置成-1，代表未分类
				book2.setCid(-1);
				bookDao.update(book2);
			}
		}
		return categoryDao.delete(id);
	}

	@Override
	public void add(List<Category> list) {
		categoryDao.add(list);
	}

	public int addOne(Category category) {
		return categoryDao.addOne(category);
	}
}
