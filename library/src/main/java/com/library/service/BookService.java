package com.library.service;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Book;

import java.util.List;

public interface BookService {
    Book get(Book t);
    PageInfo<Book> selectAllByCondition(Integer currentPage,Integer pageSize,Book book);
    PageInfo<Book> selectAll(Integer currentPage,Integer pageSize);
    int save(Book t);
    int update(Book t);
    int delete(Integer id);
}
