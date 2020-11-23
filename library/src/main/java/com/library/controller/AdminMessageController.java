package com.library.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Notice;
import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import com.library.service.NoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

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
@Api(tags = "消息的管理员接口")
public class AdminMessageController {
	@Autowired
	NoticeService noticeService;
	//
	@Autowired
	private RabbitTemplate redisTemplate;

	// 管理员查询所有消息，包括公开发布给所有人的和发布给用户的
	// 消息列表
	// http://10.10.102.163:8001/admin/message/pageSize/currentPage
	// 参数：
	// pageSize：每页显示大小
	// currentPage：当前页
	@ApiOperation("管理员查询所有消息，包括公开发布给所有人的和单独发布给某个用户的")
	@ApiImplicitParams({ @ApiImplicitParam(name = "pageSize", required = true),
			@ApiImplicitParam(name = "currentPage", required = true) })

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
	@ApiOperation("根据消息id查询消息详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "messageId", required = true) })
	@GetMapping("/detail/{messageId}")
	public Result<Notice> messageDetail(@PathVariable("messageId") Integer messageId,
			HttpServletRequest httpServletRequest) {
		Notice notice = noticeService.get(messageId);
		return new Result<>(ResultCode.SUCCESS, "查询成功", notice);
	}

	// 发布消息
	// http://10.10.102.163:8001/admin/message/add
	// 参数：表单提交
	@ApiOperation("添加一条消息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "message", required = true),
			@ApiImplicitParam(name = "uid", required = true, allowableValues = "发送到用户的id，代表消息发送到该id的用户，0代表消息发送到所有用户"),
			@ApiImplicitParam(name = "status", required = true, allowableValues = "0:消息未读，1：消息已读") })
	@PostMapping("/add")
	public Result messageAdd(Notice notice) {
		LocalDate publishDate = LocalDate.now();
		String publishString = publishDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		System.err.println(publishString);
		notice.setPublishDate(publishString);
		int num = noticeService.save(notice);
		// SendMsg(notice);
		if (num > 0) {
			return new Result<>(ResultCode.SUCCESS, "发布消息成功");
		}
		return new Result<>(ResultCode.SUCCESS, "发布消息失败");
	}

	// 管理员查询用户的消息（包括单独发送给该用户的消息+公有消息）
	// http://10.10.102.163:8001/admin/message/list/pageSize/currentPage/userid
	// 参数：用户id
	@ApiOperation("管理员查询发给某用户的所有消息（包括单独发送给该用户的消息+公有消息）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "pageSize", required = true),
			@ApiImplicitParam(name = "currentPage", required = true),
			@ApiImplicitParam(name = "userid", required = true) })
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
	@PutMapping("/update")
	public Result<Integer> update(Notice notice) {
		int row = noticeService.update(notice);
		if (row > 0) {
			return new Result(ResultCode.SUCCESS, "修改消息成功！", row);
		}
		return new Result(ResultCode.FAIL, "修改消息失败！");
	}

	@DeleteMapping("/delete/{id}")
	public Result<Integer> delete(@PathVariable("id") Integer id) {
		int row = noticeService.delete(id);
		if (row > 0) {
			return new Result(ResultCode.SUCCESS, "删除消息成功！", row);
		}
		return new Result(ResultCode.FAIL, "删除消息失败！");
	}
}
