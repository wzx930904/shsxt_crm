package com.shsxt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.base.Constant;
import com.shsxt.base.ResultInfo;
import com.shsxt.dto.UserQuery;
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
	public ResultInfo login(String userName,String password) {
		Map<String, Object> map = new HashMap<>();
		try {
			UserLoginIdentity uli = userService.login(userName, password);
//			map.put("resultCode", 1);
//			map.put("message", "Success");
//			map.put("result", uli);
			return success(uli, Constant.SUCCESS_MSG);
		} catch (ParamException e) {
//			map.put("resultCode", e.getErrorCode());
//			map.put("message", e.getMessage());
//			map.put("result", e.getMessage());
			return failure(e);
		}
	}
	
	@RequestMapping("index")
	public String index() {
		return "user";
	}
	
	@RequestMapping("find_customer_manager")
	@ResponseBody
	public List<User> findCustomerManger() {
		List<User> users = userService.findCustomerManager();
		return users;
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> selectForPage(UserQuery userQuery) {
		Map<String, Object> result = userService.selectForPage(userQuery);
		return result;
	}
	
	@RequestMapping("add")
	@ResponseBody
	public ResultInfo add(User user) {
		userService.add(user);
		return success("操作成功");
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ResultInfo update(User user) {
		userService.update(user);
		return success("操作成功");
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ResultInfo delete(String ids) {
		userService.delete(ids);
		return success("删除成功");
	}
}
