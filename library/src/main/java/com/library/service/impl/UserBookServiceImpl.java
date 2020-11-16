package com.library.service.impl;

import com.library.pojo.UserBook;
import com.library.service.UserBookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBookServiceImpl implements UserBookService{
    @Override
    public UserBook get(UserBook userBook) {
        return null;
    }

    @Override
    public List<UserBook> selectAll() {
        return null;
    }

    @Override
    public int save(UserBook userBook) {
        return 0;
    }

    @Override
    public int update(UserBook userBook) {
        return 0;
    }

    @Override
    public int delete(Integer id) {
        return 0;
    }
}
