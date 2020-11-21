package com.library.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.library.pojo.SummaryResult;

public interface SummaryDao {
	public List<SummaryResult> summary(@Param("startDate") String startDate, @Param("endDate") String endDate,
			@Param("status") int status);
}
