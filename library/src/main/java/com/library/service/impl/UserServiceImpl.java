package com.library.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.dao.UserDao;
import com.library.pojo.User;
import com.library.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserDao userDao;

    @Override
    public User get(User user) {
        return userDao.get(user);
    }

    @Override
    public User getByPWD(String username, String password) {
        //MD5加密
        password=DigestUtils.md5Hex(password);
        return userDao.getByPWD(username,password);
    }

    @Override
    public PageInfo<User> selectAll(Integer pageSize, Integer currentPage) {
        PageHelper.startPage(currentPage,pageSize);
        List<User> users = userDao.selectAll();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }

    @Override
    public int save(User user) {
        //MD5加密
        String s = DigestUtils.md5Hex(user.getPassword());

        user.setPassword(s);
        return userDao.save(user);
    }

    @Override
    public int update(User user) {
        if(user.getPassword()!=null) {
            //MD5加密
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        }
        return userDao.update(user);
    }

    @Override
    public int delete(Integer id) {
        return userDao.delete(id);
    }
}
