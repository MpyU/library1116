package com.library.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.google.gson.Gson;
import com.library.pojo.Result;
import com.library.pojo.ResultCode;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("token");
		System.out.println("token是:" + token);
		if (StringUtils.isEmpty(token)) {
			Result<String> result = new Result<>(ResultCode.FAIL, "未登陆，请先登录!");
			request.setAttribute("data", result);
			response.setStatus(HttpStatus.SC_FORBIDDEN);
			response.setCharacterEncoding("utf-8");
			response.getOutputStream().write(new Gson().toJson(result).getBytes());
			return false;
		}
		// Object = redisTemplate.opsForValue().get(token);
		return true;
	}
}
