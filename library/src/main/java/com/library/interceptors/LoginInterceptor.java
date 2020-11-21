package com.library.interceptors;

import java.time.Duration;
import java.util.Map;

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
import com.library.utils.JwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private JwtUtils jwtUtils;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("token");
		System.out.println("token是:" + token);
		// 客户没有传入token
		if (StringUtils.isEmpty(token)) {
			handleRejectMsg("未登陆，请先登录!", request, response);
			return false;
		}
		// 客户传入token已经过期
		Claims claims = null;
		try {
			claims = jwtUtils.parseJwt(token);
		} catch (MalformedJwtException e) {
			handleRejectMsg("您的token认证有误!", request, response);
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			handleRejectMsg("Inteceptor出现错误，请联系管理员!", request, response);
			return false;
		}
		Map<String, Object> user = (Map<String, Object>) claims.get("user");

		if (user != null) {
			String redisKey = "userJson" + user.get("id");
			System.out.println("redisKey:" + redisKey);
			String saveToken = (String) redisTemplate.opsForValue().get(redisKey);
			if (saveToken == null || saveToken.equals(token) == false) {
				handleRejectMsg("您已经长时间没有操作，请重新登陆!", request, response);
				return false;
			}
			// 客户传入的token匹配redis保存的数据，校验成功：客户已经登陆
			// 更新key的过期时间,自当前后的一个小时
			redisTemplate.expire(redisKey, Duration.ofHours(1));
			// redisTemplate.expire(redisKey, Duration.ofMinutes(1));
			return true;
		}
		return false;
	}

	private void handleRejectMsg(String msg, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Result<String> result = new Result<>(ResultCode.FAIL, msg);
		request.setAttribute("data", result);
		response.setStatus(HttpStatus.SC_FORBIDDEN);
		response.setCharacterEncoding("utf-8");
		response.getOutputStream().write(new Gson().toJson(result).getBytes());

	}
}
