package com.library.service;

import org.springframework.web.bind.annotation.PathVariable;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Notice;

public interface NoticeService {
	Notice get(Integer id);

	PageInfo<Notice> selectAll(Integer currentPage, Integer pageSize);

	int save(Notice t);

	int update(Notice t);

	int delete(Integer id);

	PageInfo<Notice> getByUserId(Integer id, Integer currentPage, Integer pageSize);

	public PageInfo<Notice> getUnReadMsgByUserId(@PathVariable("userId") Integer userId);

	Integer getUnReadMsgNum(Integer userId);
}
