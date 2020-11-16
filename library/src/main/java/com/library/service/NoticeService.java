package com.library.service;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Notice;

import java.util.List;

public interface NoticeService{
    Notice get(Notice t);
    PageInfo<Notice> selectAll(Integer currentPage,Integer pageSize);
    int save(Notice t);
    int update(Notice t);
    int delete(Integer id);
}
