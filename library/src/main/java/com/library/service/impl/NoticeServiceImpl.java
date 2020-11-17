package com.library.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.dao.NoticeDao;
import com.library.pojo.Notice;
import com.library.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Override
    public Notice get(Notice notice) {
        return noticeDao.get(notice);
    }

    @Override
    public PageInfo<Notice> selectAll(Integer currentPage,Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<Notice> notices = noticeDao.selectAll();
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
}
