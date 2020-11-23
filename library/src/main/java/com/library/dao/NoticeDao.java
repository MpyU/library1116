package com.library.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.library.pojo.Notice;

public interface NoticeDao extends BaseDao<Notice> {
	Notice getNoticeById(Integer id);

	List<Notice> getNoticeByUserId(@Param("uid") Integer uid);

	// 查询私有未读消息
	List<Notice> getUnReadMsgByUserId(Integer userId);
}
