package com.shsxt.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shsxt.controller.BaseController;
import com.shsxt.exception.ParamException;


@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(value = ParamException.class)
	public ResultInfo handlerParamException(ParamException paramException) {
		logger.error("参数异常：{}",paramException);
		return failure(paramException);
	}
	
	@ExceptionHandler(value = {IllegalAccessException.class, IllegalArgumentException.class})
	public ResultInfo handlerIllegalException(Exception e){
		logger.error("异常：{}", e);
		if (e instanceof IllegalAccessException) {
			return failure(((IllegalAccessException)e).getMessage());
		}
		if (e instanceof IllegalArgumentException) {
			IllegalArgumentException iae = (IllegalArgumentException)e;
			return failure(e.getMessage());
		}
		return failure("未知异常");
	}
}
