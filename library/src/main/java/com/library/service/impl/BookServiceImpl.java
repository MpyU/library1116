package com.library.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.dao.BookDao;
import com.library.dao.CategoryDao;
import com.library.pojo.Book;
import com.library.pojo.Category;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Book get(Book book) {
        Book book1 = bookDao.get(book);
        Category category = categoryDao.get(new Category(book1.getCid()));
        book1.setCategory(category);
        return book1;
    }

    @Override
    public PageInfo<Book> selectAllByCondition(Integer currentPage,Integer pageSize,Book book) {
        PageHelper.startPage(currentPage,pageSize);
        List<Book> books = bookDao.selectAllByCondition(book);
        for(Book b : books){
            Category category = categoryDao.get(new Category(b.getCid()));
            b.setCategory(category);
        }
        PageInfo<Book> pageInfo = new PageInfo<>(books);
        return pageInfo;
    }

    @Override
    public PageInfo<Book> selectAll(Integer currentPage,Integer pageSize) {
        System.out.println(currentPage+"--"+pageSize);
        PageHelper.startPage(currentPage,pageSize);
        List<Book> books = bookDao.selectAll();
        for(Book b : books){
            Category category = categoryDao.get(new Category(b.getCid()));
            b.setCategory(category);
        }
        PageInfo<Book> pageInfo = new PageInfo<>(books);
        return pageInfo;
    }

    @Override
    public int save(Book book) {
        return bookDao.save(book);
    }

    @Override
    public int update(Book book) {
        return bookDao.update(book);
    }

    @Override
    public int delete(Integer id) {
        return bookDao.delete(id);
    }
}
