package com.library.dao;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Notice;
import com.library.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeDao extends BaseDao<Notice> {
    Notice getNoticeById(Integer id);

    List<Notice> getNoticeByUserId(@Param("uid") Integer uid);
}
