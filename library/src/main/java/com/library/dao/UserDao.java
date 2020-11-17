package com.library.dao;

import com.library.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao extends BaseDao<User> {

    User getByPWD(@Param("username") String username, @Param("password") String password);
}
