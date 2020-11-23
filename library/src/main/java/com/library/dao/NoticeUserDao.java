package com.library.dao;

import com.library.pojo.NoticeUser;

import java.util.List;

public interface NoticeUserDao extends BaseDao<NoticeUser> {

	List<NoticeUser> selectAll(int userId);

	List<NoticeUser> selectAllByCondition(NoticeUser noticeUser);

	Integer getUnReadMsgNum(Integer userId);

	List<NoticeUser> getUnReadMsgByUserId(Integer userId);

}
