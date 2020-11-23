package com.library.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import com.library.pojo.User;
import com.library.service.BookService;
import com.library.service.UserBookService;
import com.library.service.UserService;
import com.library.utils.JwtUtils;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserBookService userBookService;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private BookService bookService;

	@ApiImplicitParams({ @ApiImplicitParam(name = "username", value = "用户名", defaultValue = "李四", required = true),
			@ApiImplicitParam(name = "password", value = "用户密码", defaultValue = "123", required = true),
			@ApiImplicitParam(name = "telephone", value = "联系方式", defaultValue = "12312312312"),
			@ApiImplicitParam(name = "email", value = "邮箱地址", defaultValue = "1156642797@qq.com"),
			@ApiImplicitParam(name = "headImage", value = "头像地址") })
	@ApiOperation("添加用户")
	@PostMapping("/save")
	public Result<Integer> save(User user) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		user.setRegisterDate(simpleDateFormat.format(new Date()));
		System.out.println(user);
		int row = userService.save(user);
		if (row > 0) {
			return new Result(ResultCode.SUCCESS, "注册成功！", row);
		}
		return new Result(ResultCode.FAIL, "注册失败！");
	}

	@ApiOperation("用户登录")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userStr", value = "传递一个user对象的json串", required = true) })
	@PostMapping("/login")
	public Result<String> login(@RequestBody String userStr) {
		System.out.println(userStr);
		Gson gson = new Gson();
		User user = gson.fromJson(userStr, User.class);
		System.out.println(user);
		User u = userService.getByPWD(user.getUsername(), user.getPassword());
		System.out.println("------" + u);
		if (u != null) {
			String token = jwtUtils.generateToken(u);
			String userJson = gson.toJson(u.getUsername());
			// redisTemplate.opsForValue().set("userJson", token, 36000,
			// TimeUnit.SECONDS);
			redisTemplate.opsForValue().set("userJson" + u.getId(), token, 36000, TimeUnit.SECONDS);
			Object token1 = redisTemplate.opsForValue().get("token");
			System.out.println(token1);
			List<Object> list = new ArrayList<>();
			list.add(u.getId());
			list.add(token);
			return new Result(ResultCode.SUCCESS, "登录成功！", list);
		} else {
			return new Result(ResultCode.FAIL, "用户名或密码有误！");
		}
	}

	@ApiOperation("根据ID查询单个用户")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户ID", required = true) })
	@GetMapping("/get/{id}")
	public Result<User> get(@PathVariable("id") Integer id) {
		User u = userService.get(new User(id));
		if (u != null) {
			return new Result(ResultCode.SUCCESS, "查询用户成功！", u);
		}
		return new Result(ResultCode.FAIL, "无此用户！");
	}

	@ApiOperation("分页查询所有用户信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "pageSize", value = "每页显示大小", required = true),
			@ApiImplicitParam(name = "currentPage", value = "当前页", required = true) })
	@GetMapping("/select/{pageSize}/{currentPage}")
	public Result<PageInfo<User>> select(@PathVariable("pageSize") Integer pageSize,
			@PathVariable("currentPage") Integer currentPage) {
		if (pageSize == null) {
			pageSize = 5;
		}
		if (currentPage == null) {
			currentPage = 1;
		}
		PageInfo<User> pageInfo = userService.selectAll(pageSize, currentPage);
		if (pageInfo.getList().size() > 0) {
			return new Result(ResultCode.SUCCESS, "查询所有用户成功！", pageInfo);
		}
		return new Result(ResultCode.FAIL, "查询所有用户失败！");
	}

	@ApiOperation("根据ID更新用户信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "username", value = "用户名", defaultValue = "李四", required = true),
			@ApiImplicitParam(name = "telephone", value = "联系方式", defaultValue = "12312312312"),
			@ApiImplicitParam(name = "email", value = "邮箱地址", defaultValue = "1156642797@qq.com"),
			@ApiImplicitParam(name = "headImage", value = "头像地址") })
	@PutMapping("/update")
	public Result<Integer> update(User user) {
		int row = userService.update(user);
		if (row > 0) {
			return new Result(ResultCode.SUCCESS, "用户信息修改成功！", row);
		} else {
			return new Result(ResultCode.FAIL, "用户信息修改失败！");
		}
	}

	@ApiOperation("根据ID删除用户信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户ID", required = true) })

	// http://10.10.102.163:8001/user/lend/bookId
	@PostMapping("/lend/{bookId}")
	public Result lendBook(@PathVariable("bookId") Integer bookId, HttpServletRequest httpServletRequest) {
		Map<String, Object> user = mapparseHeaderToUser(httpServletRequest);
		Integer userID = (Integer) user.get("id");
		// 用户借书数量是否达到三本
		boolean canLean = userBookService.canLendBook(userID);
		// 书的余量
		int bookRemain = bookService.bookNum(bookId).getBookCount();
		if (canLean && bookRemain > 0) {
			Map<String, Object> map = new HashMap();
			map.put("userId", userID);
			map.put("bookId", bookId);
			map.put("lendDate", new Date());
			map.put("returnDate", new Date());

			int num = userBookService.lendBook(map);
			bookService.subBook(bookId, 1);
			if (num > 0) {
				return new Result<>(ResultCode.SUCCESS, "借书成功");
			} else {
				return new Result<>(ResultCode.SUCCESS, "借书失败");
			}
		} else {
			if (bookRemain <= 0) {
				return new Result<>(ResultCode.SUCCESS, "该书已经借完");
			}
			return new Result<>(ResultCode.SUCCESS, "借书数量已经达到三本");
		}

	}

	// 还书
	// http://10.10.102.163:8001/user/returnBook/bookId
	@PutMapping("returnBook/{bookId}")
	public Result returnBook(@PathVariable("bookId") Integer bookId, HttpServletRequest httpServletRequest) {
		Map<String, Object> user = mapparseHeaderToUser(httpServletRequest);
		Integer userId = (Integer) user.get("id");
		int result = userBookService.returnBook(userId, bookId);
		if (result > 0) {
			return new Result(ResultCode.SUCCESS, "还书成功");
		} else {
			return new Result(ResultCode.SUCCESS, "还书失败");
		}

	}

	// 从请求中解析出use
	private Map<String, Object> mapparseHeaderToUser(HttpServletRequest httpServletRequest) {
		// token命名为Authization
		String header = httpServletRequest.getHeader("Authization");
		Claims claims = jwtUtils.parseJwt(header);
		Map<String, Object> user = (Map<String, Object>) claims.get("user");
		return user;
	}

	@DeleteMapping("/delete/{id}")
	public Result<Integer> delete(@PathVariable("id") Integer id) {
		int result = userService.delete(id);
		if (result > 0) {
			return new Result(ResultCode.SUCCESS, "删除用户成功");
		} else {
			return new Result(ResultCode.SUCCESS, "删除用户失败");
		}
	}
}
