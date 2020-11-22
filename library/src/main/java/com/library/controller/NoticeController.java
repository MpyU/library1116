package com.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

@RestController
@RequestMapping("/notice")
@CrossOrigin
@Api(tags = "用户消息接口")
public class NoticeController {

	@Autowired
	NoticeService noticeService;

	@ApiOperation("查询消息详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", required = true) })
	@GetMapping("/get/{id}")
	public Result<Notice> get(@PathVariable("id") Integer id) {

		Notice n = noticeService.get(id);
		if (n != null) {
			return new Result<>(ResultCode.SUCCESS, "查询消息成功！", n);
		}
		return new Result(ResultCode.FAIL, "查询消息失败！");
	}

	@ApiOperation("用户查询自己所有的消息（已读消息+未读消息）")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", required = true),
			@ApiImplicitParam(name = "pageSize", required = true),
			@ApiImplicitParam(name = "currentPage", required = true) })
	// 用户查询自己所有的消息（已读+未读）
	@GetMapping("/getByUserId/{userId}/{pageSize}/{currentPage}")
	public Result<PageInfo<Notice>> getByUserId(@PathVariable("userId") Integer id,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage) {
		PageInfo<Notice> pageInfo = noticeService.getByUserId(id, currentPage, pageSize);
		if (pageInfo.getList().size() > 0) {
			return new Result<>(ResultCode.SUCCESS, "查询消息成功！", pageInfo);
		}
		return new Result(ResultCode.FAIL, "查询消息失败！");
	}

	@ApiOperation("根据用户id查询用户所有未读的消息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", required = true) })
	// 返回该用户id未读的消息
	@GetMapping("/getUnReadMsgByUserId/{userId}")
	public Result<PageInfo<Notice>> getUnReadMsgByUserId(@PathVariable("userId") Integer userId) {
		PageInfo<Notice> pageInfo = noticeService.getUnReadMsgByUserId(userId);
		return new Result<PageInfo<Notice>>(ResultCode.SUCCESS, "查询成功", pageInfo);

	}

	@ApiOperation("返回该用户未读的消息数量")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", required = true) })
	// 返回该用户id未读的消息数量
	@GetMapping("/getUnReadMsgNum/{userId}")
	public Result<Integer> getUnReadMsgNum(@PathVariable("userId") Integer userId) {
		Integer userUnReadMsgNum = noticeService.getUnReadMsgNum(userId);
		return new Result<Integer>(ResultCode.SUCCESS, "查询成功", userUnReadMsgNum);

	}

	// @GetMapping("/select/{pageSize}/{currentPage}")
	// public Result<PageInfo<Notice>> select(@PathVariable("pageSize") Integer
	// pageSize,
	// @PathVariable("currentPage") Integer currentPage) {
	//
	// if (pageSize == null) {
	// pageSize = 5;
	// }
	// if (currentPage == null) {
	// currentPage = 1;
	// }
	//
	// PageInfo<Notice> pageInfo = noticeService.selectAll(currentPage,
	// pageSize);
	// if (pageInfo.getList().size() > 0) {
	// return new Result(ResultCode.SUCCESS, "查询所有消息成功！", pageInfo);
	// }
	// return new Result(ResultCode.FAIL, "查询所有消息失败！");
	// }
	//
	// @PostMapping("/save")
	// public Result<Integer> save(Notice notice) {
	// SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	// String date = simpleDateFormat.format(new Date());
	// notice.setPublishDate(date);
	// int row = noticeService.save(notice);
	// if (row > 0) {
	// return new Result(ResultCode.SUCCESS, "添加消息成功！", row);
	// }
	// return new Result(ResultCode.FAIL, "添加消息失败！");
	// }

}
