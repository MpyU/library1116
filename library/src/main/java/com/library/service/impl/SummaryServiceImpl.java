package com.library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.dao.SummaryDao;
import com.library.pojo.SummaryResult;
import com.library.service.SummaryService;

@Service
public class SummaryServiceImpl implements SummaryService {
	@Autowired
	SummaryDao summaryDao;

	@Override
	public PageInfo<SummaryResult> summary(String startDay, String endDay, int status, Integer pageSize,
			Integer currentPage) {
		PageHelper.startPage(currentPage, pageSize);
		List<SummaryResult> list = summaryDao.summary(startDay, endDay, status);
		PageInfo pageHelper = new PageInfo(list);
		return pageHelper;
	}
}
