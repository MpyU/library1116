package com.library.service;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Book;

public interface BookService {
    Book get(Book t);
    PageInfo<Book> selectAllByCondition(Integer currentPage,Integer pageSize,Book book);
    PageInfo<Book> selectAll(Integer currentPage,Integer pageSize);
    int save(Book t);
    int update(Book t);
    int delete(Integer id);
    //查询该bookId还剩余的数量
    Book bookNum(Integer bookId);

    //减库存
    int subBook(int bookId,int num);


    int bookUp(Integer bookId);

    int bookDown(Integer bookId);

    //点击量加1
    int hitsAdd(Integer id);

    //把月点击量设置为0
    int monthClickToZero();
    //把日点击量设置为0
    int dayClickToZero();
}
