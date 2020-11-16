package com.library.service;

import com.github.pagehelper.PageInfo;
import com.library.pojo.User;

import java.util.List;

public interface UserService {
    User get(User t);
    PageInfo<User> selectAll(Integer pageSize, Integer currentPage);
    int save(User t);
    int update(User t);
    int delete(Integer id);
}
