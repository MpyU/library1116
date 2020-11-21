package com.library.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Notice;
import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import com.library.service.NoticeService;

//http://10.10.102.163:8001/admin/message/pageSize/currentPage

/**
 *
 * 发送信息用异步任务，传送到RabbitMQ,再读取RabbitMQ保存到Redis,+数据库
 * uid+message为key，用Redis的List保存未读消息的id,看到后就删除
 * 缺点：必须为redis设置过期时间，否则容易占满内存
 */

/**

 */
@RestController
@CrossOrigin
@RequestMapping("/admin/message")
public class AdminMessageController {
	@Autowired
	NoticeService noticeService;
	//
	@Autowired
	private RabbitTemplate redisTemplate;

	// 消息列表
	// http://10.10.102.163:8001/admin/message/pageSize/currentPage
	// 参数：
	// pageSize：每页显示大小
	// currentPage：当前页
	@GetMapping("/{pageSize}/{currentPage}")
	public Result<PageInfo<Notice>> messgae(@PathVariable("pageSize") Integer pageSize,
			@PathVariable("currentPage") Integer currentPage) {
		PageInfo<Notice> pageInfo = noticeService.selectAll(currentPage, pageSize);
		if (pageInfo.getList().size() > 0) {
			return new Result<>(ResultCode.SUCCESS, "查询成功", pageInfo);
		} else {
			return new Result<>(ResultCode.FAIL, "查询失败");
		}

	}

	// 消息详情
	// http://10.10.102.163:8001/admin/message/detail/messageId
	// 参数：
	// bookid：查询的消息的ID
	@GetMapping("/detail/{messageId}")
	public Result<Notice> messageDetail(@PathVariable("messageId") Integer messageId,
			HttpServletRequest httpServletRequest) {
		Notice notice = noticeService.get(messageId);
		return new Result<>(ResultCode.SUCCESS, "查询成功", notice);
	}

	// 发布消息
	// http://10.10.102.163:8001/admin/message/add
	// 参数：表单提交
	@PostMapping("/add")
	public Result messageAdd(Notice notice) {
		int num = noticeService.save(notice);
		// SendMsg(notice);
		if (num > 0) {
			return new Result<>(ResultCode.SUCCESS, "发布消息成功");
		}
		return new Result<>(ResultCode.SUCCESS, "发布消息失败");
	}

	// 查询用户的消息
	// http://10.10.102.163:8001/admin/message/list/pageSize/currentPage/userid
	// 参数：用户id
	@GetMapping("/list/{pageSize}/{currentPage}/{userid}")
	public Result<PageInfo<Notice>> messageDetail(@PathVariable("currentPage") Integer currentPage,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("userid") Integer userid,
			HttpServletRequest httpServletRequest) {
		PageInfo<Notice> pageInfo = noticeService.getByUserId(userid, currentPage, pageSize);

		return new Result<PageInfo<Notice>>(ResultCode.SUCCESS, "查询成功", pageInfo);
	}
	// public void SendMsg(Notice notice) {
	// System.out.println("进入发布消息页面");
	// redisTemplate.convertAndSend("","notice_queue",notice);
	// }

}
