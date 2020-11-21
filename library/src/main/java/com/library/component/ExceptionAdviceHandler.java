package com.library.component;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.pojo.Result;
import com.library.pojo.ResultCode;

@ControllerAdvice
@ResponseBody
public class ExceptionAdviceHandler {

	@ExceptionHandler(value = { java.lang.Exception.class })
	public Result<String> handlerException(Exception ex) {
		System.err.println("全局异常处理");
		return new Result<>(ResultCode.FAIL, "出现异常了,请联系管理员", ex.getMessage());

	}

}
