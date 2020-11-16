package com.library.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.dao.BookDao;
import com.library.pojo.Book;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookDao bookDao;

    @Override
    public Book get(Book book) {
        return bookDao.get(book);
    }

    @Override
    public PageInfo<Book> selectAllByCondition(Integer currentPage,Integer pageSize,Book book) {
        PageHelper.startPage(currentPage,pageSize);
        List<Book> books = bookDao.selectAllByCondition(book);
        PageInfo<Book> pageInfo = new PageInfo<>(books);
        return pageInfo;
    }

    @Override
    public PageInfo<Book> selectAll(Integer currentPage,Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<Book> books = bookDao.selectAll();
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
