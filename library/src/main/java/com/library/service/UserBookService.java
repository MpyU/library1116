package com.library.service;

import com.library.pojo.UserBook;

import java.util.List;

public interface UserBookService {
    UserBook get(UserBook t);
    List<UserBook> selectAll();
    int save(UserBook t);
    int update(UserBook t);
    int delete(Integer id);
}
