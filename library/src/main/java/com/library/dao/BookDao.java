package com.library.dao;

import com.library.pojo.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookDao extends BaseDao<Book> {
    List<Book> selectAllByCondition(Book book);

    Book bookNum(Integer id);

    //减库存
    int subBook(@Param("bookId")int bookId,@Param("num")int num);
}
