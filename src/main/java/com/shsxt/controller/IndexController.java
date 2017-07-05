package com.shsxt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shsxt.annotation.SystemLog;
import com.shsxt.service.UserService;
import com.shsxt.util.LoginUserUtil;
import com.shsxt.vo.UserLoginIdentity;

@Controller
public class IndexController  extends BaseController{
	
	@Resource
	private UserService userService;
	
	@RequestMapping("index")
	//@SystemLog(description="首页")
	public String index(Model model,HttpServletRequest request) {
		return "index";
	}

	@RequestMapping("main")
	public String main(Model model,HttpServletRequest request) {
		Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
		UserLoginIdentity uli =userService.findLoginUser(userId);
		model.addAttribute("currentUser", uli);
		return "main";
	}
}
