package com.shsxt.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.shsxt.base.Constant;
import com.shsxt.base.ResultInfo;
import com.shsxt.exception.ParamException;

public class BaseController {

	@ModelAttribute
	public void conPath(Model model,HttpServletRequest request) {
		String contextPath = request.getContextPath();
		model.addAttribute("ctx", contextPath);
	}
	
	public ResultInfo success(Object result,String...message) {
		ResultInfo ri = new ResultInfo();
		ri.setResult(result);
		ri.setResultCode(Constant.SUCCESS_CODE);
		if (message == null || message.length == 0 || StringUtils.isBlank(message[0])) {
			ri.setResultMessage(Constant.SUCCESS_MSG);
		} else {
			ri.setResultMessage(message[0]);
		}
		return ri;
	}
	
	public ResultInfo failure(ParamException pe) {
		ResultInfo ri = new ResultInfo();
		ri.setResultCode(pe.getErrorCode());
		ri.setResultMessage(pe.getMessage());
		ri.setResult(pe.getMessage());
		return ri;
	}
	
	public ResultInfo failure(Integer code,String message) {
		ResultInfo ri = new ResultInfo();
		ri.setResultCode(code);
		ri.setResultMessage(message);
		ri.setResult(message);
		return ri;
	}
	
	public ResultInfo failure(String... message) {
		ResultInfo resultInfo = new ResultInfo();
		resultInfo.setResultCode(Constant.ERROR_CODE);
		if (message == null || message.length == 0 || StringUtils.isBlank(message[0])) {
			resultInfo.setResultMessage(Constant.ERROR_MSG);
			resultInfo.setResult(Constant.ERROR_MSG);
		} else {
			resultInfo.setResultMessage(message[0]);
			resultInfo.setResult(message[0]);
		}
		return resultInfo;
	}
}
