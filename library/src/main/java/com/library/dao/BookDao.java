package com.library.dao;

import com.library.pojo.Book;
import com.library.pojo.User;

import java.util.List;

public interface BookDao extends BaseDao<Book> {
    List<Book> selectAllByCondition(Book book);
}
