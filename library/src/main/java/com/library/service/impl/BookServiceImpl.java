package com.library.service.impl;

import com.library.pojo.Book;
import com.library.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Override
    public Book get(Book book) {
        return null;
    }

    @Override
    public List<Book> selectAll() {
        return null;
    }

    @Override
    public int save(Book book) {
        return 0;
    }

    @Override
    public int update(Book book) {
        return 0;
    }

    @Override
    public int delete(Integer id) {
        return 0;
    }
}
