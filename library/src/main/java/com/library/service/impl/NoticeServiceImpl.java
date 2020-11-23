package com.library.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.dao.NoticeDao;
import com.library.dao.NoticeUserDao;
import com.library.dao.UserDao;
import com.library.pojo.Notice;
import com.library.pojo.NoticeUser;
import com.library.pojo.User;
import com.library.service.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	NoticeUserDao noticeUserDao;

	// 获取消息详情
	public Notice get(Integer mid) {
		Notice notice = noticeDao.getNoticeById(mid);
		System.out.println("消息是：" + notice);
		if (notice.getUid() != null && notice.getUid() != 0) {
			// 消息是发给这个用户的
			User user = userDao.get(new User(notice.getUid()));
			System.out.println("user:" + user);
			notice.setUser(user);
			// //状态1是管理员，可以看到所有消息，并且不需要设置为已经读
			// notice.setStatus(1);
			// //等于0代表全发,更新该用户的消息
			// if(notice.getUid()==0 && uid!=notice.getUid()){
			// noticeUserDao.update(new NoticeUser(uid,mid,1));
			// }else{
			// noticeDao.update(notice);
			// }

		}

		return notice;
	}

	// 管理员查询所有消息
	@Override
	public PageInfo<Notice> selectAll(Integer currentPage, Integer pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<Notice> notices = noticeDao.selectAll();
		for (Notice notice : notices) {
			Integer userId = notice.getUid();
			if (userId != null && userId != 0) {
				User user = userDao.get(new User(userId));
				notice.setUser(user);
			}
		}
		PageInfo<Notice> pageInfo = new PageInfo<>(notices);
		return pageInfo;
	}

	// 保存信息
	@Override
	public int save(Notice notice) {
		int result = noticeDao.save(notice);
		if (notice.getUid() != null && notice.getUid() == 0) {
			List<User> list = userDao.selectAll();
			// 保存公用消息
			for (User user : list) {
				result = noticeUserDao.save(new NoticeUser(user.getId(), notice.getId(), 0));
				if (result == 0) {
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

	// 管理员查询用户的消息（包括单独发送给该用户的消息+公有消息），由于不是用户读取的，所有信息的已读标志不用设置成1
	@Override
	public PageInfo<Notice> getByUserId(Integer uid, Integer currentPage, Integer pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		// 查出用户所有信息
		List<Notice> notices = noticeDao.getNoticeByUserId(uid);
		for (Notice notice : notices) {
			Integer status = notice.getStatus();
			Integer userId = notice.getUid();
			if (status != null && status != 0 && userId != null) {
				User user = userDao.get(new User(userId));
				notice.setUser(user);
			}
		}
		PageInfo<Notice> pageInfo = new PageInfo<>(notices);
		return pageInfo;
	}

	public PageInfo<Notice> getUnReadMsgByUserId(Integer currentPage,Integer pageSize,  Integer userId) {
		List<Notice> unReadNotice = new LinkedList<Notice>();
		// 查询公有未读消息，并且设置为已读
		PageHelper.startPage(currentPage,pageSize);
		List<NoticeUser> listNoticeUser = noticeUserDao.getUnReadMsgByUserId(userId);
		for (NoticeUser noticeUser : listNoticeUser) {
			if (noticeUser.getNid() != null) {
				Notice notice = noticeDao.get(new Notice(noticeUser.getNid()));
				unReadNotice.add(notice);

				noticeUser.setStatus(1);
				noticeUserDao.update(noticeUser);
			}

		}
		// 查询私有未读消息,并设置已读
		List<Notice> listPriUnReadMsg = noticeDao.getUnReadMsgByUserId(userId);
		for (Notice notice : listPriUnReadMsg) {
			notice.setStatus(1);
			noticeDao.update(notice);
		}
		// 公有未读信息+私有未读信息加起来就是所有未读消息
		unReadNotice.addAll(listPriUnReadMsg);
		PageInfo<Notice> pageInfo = new PageInfo<>(unReadNotice);
		return pageInfo;

	}

	@Override
	public Integer getUnReadMsgNum(Integer userId) {
		Integer privateUnreadNum=noticeDao.getUnReadNumMsgByUserId(userId);
		Integer publicUnreadNum=noticeUserDao.getUnReadMsgNum(userId);
		int total=0;
		if(privateUnreadNum!=null){
			total=total+privateUnreadNum;
		}
		if(publicUnreadNum!=null){
			total=total+publicUnreadNum;
		}
		return total;
	}

	@Override
	public List<Notice> selectByMessage(Integer pageSize, Integer currentPage, String message) {
		message="%"+message+"%";
		PageHelper.startPage(currentPage,pageSize);
		return noticeDao.selectByMessage(message);
	}

}
