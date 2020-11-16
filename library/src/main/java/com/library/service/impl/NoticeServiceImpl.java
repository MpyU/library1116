package com.library.service.impl;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Notice;
import com.library.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Override
    public Notice get(Notice notice) {
        return null;
    }

    @Override
    public PageInfo<Notice> selectAll(Integer currentPage,Integer pageSize) {
        return null;
    }

    @Override
    public int save(Notice notice) {
        return 0;
    }

    @Override
    public int update(Notice notice) {
        return 0;
    }

    @Override
    public int delete(Integer id) {
        return 0;
    }
}
