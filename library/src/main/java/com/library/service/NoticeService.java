package com.library.service;

import com.library.pojo.Notice;

import java.util.List;

public interface NoticeService{
    Notice get(Notice t);
    List<Notice> selectAll();
    int save(Notice t);
    int update(Notice t);
    int delete(Integer id);
}
