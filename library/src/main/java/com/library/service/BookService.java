package com.library.service;

import com.library.pojo.Book;

import java.util.List;

public interface BookService {
    Book get(Book t);
    List<Book> selectAll();
    int save(Book t);
    int update(Book t);
    int delete(Integer id);
}
