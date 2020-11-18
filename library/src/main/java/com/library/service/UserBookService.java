package com.library.service;

import com.github.pagehelper.PageInfo;
import com.library.pojo.UserBook;

import java.util.Map;

public interface UserBookService {
    UserBook get(UserBook t);
    PageInfo<UserBook> selectAll(Integer currentPage,Integer pageSize);
    int save(UserBook t);
    int update(UserBook t);
    int delete(Integer id);

    PageInfo<UserBook> getByUidOrBid(Integer currentPage, Integer pageSize, UserBook userBook);

    public Boolean canLendBook(Integer userId);

    public int lendBook(Map<String,Object> map);
}
