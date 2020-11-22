package com.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.library.interceptors.LoginInterceptor;

@Component
public class MyWebMvcConfig implements WebMvcConfigurer {

	@Autowired
	LoginInterceptor loginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/user/login",
		// "/user/save", "/swagger-resources/**", "/webjars/**", "/v2/**",
		// "/swagger-ui.html/**");
	}

}
