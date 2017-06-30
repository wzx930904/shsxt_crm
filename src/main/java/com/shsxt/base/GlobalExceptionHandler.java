package com.shsxt.base;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shsxt.controller.BaseController;
import com.shsxt.exception.ParamException;


@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController{

	@ExceptionHandler(value = ParamException.class)
	public ResultInfo handlerParamException(ParamException paramException) {
		return failure(paramException);
	}
	
	@ExceptionHandler(value = {IllegalAccessException.class, IllegalArgumentException.class})
	public ResultInfo handlerIllegalException(Exception e){
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
