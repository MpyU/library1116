package com.library.dao;

import com.library.pojo.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeDao extends BaseDao<Notice> {
	Notice getNoticeById(Integer id);

	List<Notice> getNoticeByUserId(@Param("uid") Integer uid);

	// 查询私有未读消息
	List<Notice> getUnReadMsgByUserId(Integer userId);

	Integer getUnReadNumMsgByUserId(Integer userId);

    List<Notice> selectByMessage(String message);
}
