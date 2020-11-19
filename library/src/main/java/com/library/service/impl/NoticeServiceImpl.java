package com.library.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.dao.NoticeDao;
import com.library.dao.NoticeUserDao;
import com.library.dao.UserDao;
import com.library.pojo.Notice;
import com.library.pojo.NoticeUser;
import com.library.pojo.User;
import com.library.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    NoticeUserDao noticeUserDao;


    public Notice get(Integer mid) {
        Notice notice = noticeDao.getNoticeById(mid);
        System.out.println("消息是："+notice);
     if(notice.getUid()!=0){
         //消息是发给这个用户的
         User user = userDao.get(new User(notice.getUid()));
         System.out.println("user:"+user);
         notice.setUser(user);
//         //状态1是管理员，可以看到所有消息，并且不需要设置为已经读
//         notice.setStatus(1);
//         //等于0代表全发,更新该用户的消息
//         if(notice.getUid()==0 && uid!=notice.getUid()){
//             noticeUserDao.update(new NoticeUser(uid,mid,1));
//         }else{
//             noticeDao.update(notice);
//         }

     }


        return notice;
    }



    @Override
    public PageInfo<Notice> selectAll(Integer currentPage,Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<Notice> notices = noticeDao.selectAll();
        for(Notice notice : notices){
            int userId = notice.getStatus();
            if(userId != 0){
                User user = userDao.get(new User(userId));
                notice.setUser(user);
            }
        }
        PageInfo<Notice> pageInfo = new PageInfo<>(notices);
        return pageInfo;
    }

    @Override
    public int save(Notice notice) {
        int result=noticeDao.save(notice);
        if(notice.getStatus()!=null && notice.getStatus()==0){
            List<User> list=userDao.selectAll();
            //保存公用消息
            for (User user:list) {
                result=noticeUserDao.save(new NoticeUser(user.getId(),notice.getId(),0));
                if(result==0){
                    throw new RuntimeException("消息添加错误");
                }
            }
        }
        return result;
    }

    @Override
    public int update(Notice notice) {
        return noticeDao.update(notice);
    }

    @Override
    public int delete(Integer id) {
        return noticeDao.delete(id);
    }

    @Override
    public PageInfo<Notice> getByUserId(Integer uid,Integer currentPage,Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<Notice> notices = noticeDao.getNoticeByUserId(uid);
        for(Notice notice : notices){
            int status = notice.getStatus();
            if(status != 0){
                User user = userDao.get(new User(status));
                notice.setUser(user);
            }
        }
        PageInfo<Notice> pageInfo = new PageInfo<>(notices);
        return pageInfo;
    }
}
