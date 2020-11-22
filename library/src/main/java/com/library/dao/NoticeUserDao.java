package com.library.dao;

import java.util.List;

import com.library.pojo.NoticeUser;

public interface NoticeUserDao extends BaseDao<NoticeUser> {

	List<NoticeUser> selectAll(int userId);

	List<NoticeUser> selectAllByCondition(NoticeUser noticeUser);

	Integer getUnReadMsgNum(Integer userId);

	List<NoticeUser> getUnReadMsgByUserId(Integer userId);

}
