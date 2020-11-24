package com.library.dao;

import com.library.pojo.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookDao extends BaseDao<Book> {
    List<Book> selectAllByCondition(Book book);

    Book bookNum(Integer id);

    //减库存
    int subBook(@Param("bookId")int bookId,@Param("num")int num);


    int bookUp(Integer bookId);

    int bookDown(Integer bookId);
    //点击量加1
   int hitsAdd(Integer id);
//把月点击量设置为0
    int monthClickToZero();
//把日点击量设置为0
    int dayClickToZero();
}
