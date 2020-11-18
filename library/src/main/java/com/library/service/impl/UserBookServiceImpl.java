package com.library.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.dao.BookDao;
import com.library.dao.UserBookDao;
import com.library.dao.UserDao;
import com.library.pojo.Book;
import com.library.pojo.User;
import com.library.pojo.UserBook;
import com.library.service.UserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserBookServiceImpl implements UserBookService{

    @Autowired
    private UserBookDao userBookDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookDao bookDao;

    @Override
    public UserBook get(UserBook userBook) {
        UserBook ub = userBookDao.get(userBook);
        User user = userDao.get(new User(ub.getUid()));
        Book book = bookDao.get(new Book(ub.getBid()));
        ub.setUser(user);
        ub.setBook(book);
        return ub;
    }

    @Override
    public PageInfo<UserBook> selectAll(Integer currentPage,Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<UserBook> userBooks = userBookDao.selectAll();
        for(UserBook userBook : userBooks){
            User user = userDao.get(new User(userBook.getUid()));
            Book book = bookDao.get(new Book(userBook.getBid()));
            userBook.setUser(user);
            userBook.setBook(book);
        }
        PageInfo<UserBook> pageInfo = new PageInfo<>(userBooks);
        return pageInfo;
    }

    @Override
    public int save(UserBook userBook) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss");
        String date = simpleDateFormat.format(new Date());
        userBook.setLendDate(date);
        return userBookDao.save(userBook);
    }

    @Override
    public int update(UserBook userBook) {
        return userBookDao.update(userBook);
    }

    @Override
    public int delete(Integer id) {
        return userBookDao.delete(id);
    }

    @Override
    public PageInfo<UserBook> getByUidOrBid(Integer currentPage, Integer pageSize, UserBook userBook) {
        PageHelper.startPage(currentPage,pageSize);
        List<UserBook> userBooks = userBookDao.getByUidOrBid(userBook);
        for(UserBook ub : userBooks){
            Book book = bookDao.get(new Book(ub.getBid()));
            User user = userDao.get(new User(ub.getUid()));
            ub.setUser(user);
            ub.setBook(book);
        }
        PageInfo<UserBook> pageInfo = new PageInfo<>(userBooks);
        return pageInfo;
    }
    //查询还在借的书，看是否已经借够三本
    public Boolean canLendBook(Integer userId){
        List<UserBook> list= userBookDao.hasLendBooks(userId);
        if(list.size()<3){
            return true;
        }
        return false;
    }
    public int lendBook(Map<String,Object> map){
        return userBookDao.lendBook(map);

    }
}
