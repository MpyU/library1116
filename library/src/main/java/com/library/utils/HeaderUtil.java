package com.library.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

@Component
public class HeaderUtil {
	@Autowired
	private JwtUtils jwtUtils;

	// 从请求中解析出use
	public Map<String, Object> mapparseHeaderToUser(HttpServletRequest httpServletRequest) {
		// token命名为Authization
		String header = httpServletRequest.getHeader("Authization");
		Claims claims = jwtUtils.parseJwt(header);
		Map<String, Object> user = (Map<String, Object>) claims.get("user");
		return user;
	}
}
