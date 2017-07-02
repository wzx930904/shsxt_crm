package com.shsxt.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.base.ResultInfo;
import com.shsxt.model.Role;
import com.shsxt.service.RoleService;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController{

	@Resource
	private RoleService roleService;
	
	@RequestMapping("index")
	public String index(){
		return "role";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> findAll() {
		Map<String, Object> result = roleService.findAll();
		return result;
	}
	
	@RequestMapping("add")
	@ResponseBody
	public ResultInfo add(String roleName,String roleRemark) {
		roleService.add(roleName,roleRemark);
		return success("添加成功");
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ResultInfo update(Integer id,String roleName,String roleRemark) {
		roleService.update(id,roleName,roleRemark);
		return success("修改成功");
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ResultInfo delete(String ids){
		roleService.deleteBatch(ids);
		return success("删除成功");
	}
	
}
