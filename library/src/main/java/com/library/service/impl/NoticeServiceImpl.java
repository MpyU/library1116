package com.library.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.dao.NoticeDao;
import com.library.dao.UserDao;
import com.library.pojo.Notice;
import com.library.pojo.User;
import com.library.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Notice get(Integer id) {
        Notice notice = noticeDao.getNoticeById(id);
        if(notice.getStatus() != 0){
            User user = userDao.get(new User(notice.getStatus()));
            notice.setUser(user);
        }
        return notice;
    }

    @Override
    public PageInfo<Notice> selectAll(Integer currentPage,Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<Notice> notices = noticeDao.selectAll();
        for(Notice notice : notices){
            int userId = notice.getStatus();
            if(userId != 0){
                User user = userDao.get(new User(userId));
                notice.setUser(user);
            }
        }
        PageInfo<Notice> pageInfo = new PageInfo<>(notices);
        return pageInfo;
    }

    @Override
    public int save(Notice notice) {
        return noticeDao.save(notice);
    }

    @Override
    public int update(Notice notice) {
        return noticeDao.update(notice);
    }

    @Override
    public int delete(Integer id) {
        return noticeDao.delete(id);
    }

    @Override
    public PageInfo<Notice> getByUserId(Integer uid,Integer currentPage,Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        noticeDao.getNoticeByUserId(uid);
        List<Notice> notices = noticeDao.getNoticeByUserId(uid);
        for(Notice notice : notices){
            int status = notice.getStatus();
            if(status != 0){
                User user = userDao.get(new User(status));
                notice.setUser(user);
            }
        }
        PageInfo<Notice> pageInfo = new PageInfo<>(notices);
        return pageInfo;
    }
}
