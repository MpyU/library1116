package com.library.service;

import com.github.pagehelper.PageInfo;
import com.library.pojo.SummaryResult;

public interface SummaryService {
	public PageInfo<SummaryResult> summary(String startDay, String endDayte, int status, Integer pageSize,
			Integer currentPage);
}
