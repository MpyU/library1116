package com.library.dao;

import com.library.pojo.UserBook;

import java.util.List;
import java.util.Map;

public interface UserBookDao extends BaseDao<UserBook> {

    List<UserBook> getByUidOrBid(UserBook userBook);
    public List<UserBook> hasLendBooks(Integer userId);

    public Integer lendBook(Map<String,Object> map);
}
