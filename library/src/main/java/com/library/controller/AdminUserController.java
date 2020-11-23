package com.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import com.library.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/admin/user/")
public class AdminUserController {
	@Autowired
	UserService userService;

	// 查询所有用户信息
	// http://10.10.102.163:8001/admin/user/select/pageSize/currentPage
	// 参数：
	// pageSize：每页显示大小
	// currentPage：当前页
	@GetMapping("/select/{pageSize}/{currentPage}")
	public String userList(@PathVariable("pageSize") Integer pageSize,
			@PathVariable("currentPage") Integer currentPage) {
		return "redirect:/user/select/" + pageSize + "/" + currentPage;
	}

	// 删除用户信息
	// http://10.10.102.163:8001/admin/user/delete/userId
	// 参数：
	// userId：需要删除的用户ID
	@DeleteMapping("/delete/{userId}")
	public Result<Integer> delete(@PathVariable("userId") Integer userId) {
		int row = userService.delete(userId);
		if (row > 0) {
			return new Result(ResultCode.SUCCESS, "用户删除成功！", row);
		} else {
			return new Result(ResultCode.FAIL, "用户删除失败！");
		}
	}
}
