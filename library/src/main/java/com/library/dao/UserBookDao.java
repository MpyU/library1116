package com.library.dao;

import com.library.pojo.Book;
import com.library.pojo.UserBook;

import java.util.List;

public interface UserBookDao extends BaseDao<UserBook> {

    List<UserBook> getByUidOrBid(UserBook userBook);
}
