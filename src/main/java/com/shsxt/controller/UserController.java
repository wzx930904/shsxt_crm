package com.shsxt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.exception.ParamException;
import com.shsxt.model.User;
import com.shsxt.service.UserService;
import com.shsxt.vo.UserLoginIdentity;

@Controller
@RequestMapping("user")
public class UserController  extends BaseController{
	
	@Resource
	private UserService userService;

	@RequestMapping("login")
	@ResponseBody
	public Map<String, Object> login(String userName,String password) {
		Map<String, Object> map = new HashMap<>();
		try {
			UserLoginIdentity uli = userService.login(userName, password);
			map.put("resultCode", 1);
			map.put("message", "Success");
			map.put("result", uli);
		} catch (ParamException e) {
			map.put("resultCode", e.getErrorCode());
			map.put("message", e.getMessage());
			map.put("result", e.getMessage());
		}
		return map;
	}
	
	@RequestMapping("find_customer_manager")
	@ResponseBody
	public List<User> findCustomerManger() {
		List<User> users = userService.findCustomerManager();
		return users;
	}
}
