package com.library.service;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Notice;

import java.util.List;

public interface NoticeService {
	Notice get(Integer id);

	PageInfo<Notice> selectAll(Integer currentPage, Integer pageSize);

	int save(Notice t);

	int update(Notice t);

	int delete(Integer id);

	PageInfo<Notice> getByUserId(Integer id, Integer currentPage, Integer pageSize);

	public PageInfo<Notice> getUnReadMsgByUserId(Integer currentPage,Integer pageSize,   Integer userId);

	Integer getUnReadMsgNum(Integer userId);

    List<Notice> selectByMessage(Integer pageSize, Integer currentPage, String message);
}
