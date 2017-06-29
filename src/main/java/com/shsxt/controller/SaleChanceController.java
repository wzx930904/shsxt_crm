package com.shsxt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.dto.SaleChanceQuery;
import com.shsxt.exception.ParamException;
import com.shsxt.model.SaleChance;
import com.shsxt.service.SaleChanceService;
import com.shsxt.util.CookieUtil;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController{
	
	@Resource
	private SaleChanceService saleChanceService;
	
	@RequestMapping("index")
	public String index() {
		return "sale_chance";//跳转到sale_chance.ftl页面
	}

	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> selectForPage(SaleChanceQuery query) {
		Map<String, Object> map = new HashMap<>();
		map = saleChanceService.selectForPage(query);
		return map;
	}
	
	@RequestMapping("add_update")
	@ResponseBody
	public Map<String, Object> addOrUpdate(SaleChance saleChance,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String userName = CookieUtil.getCookieValue(request, "userName");
		try {
			saleChanceService.addOrUpdate(saleChance,userName);
			map.put("resultCode", 1);
			map.put("resultMessage", "操作成功");
			map.put("result", "操作成功");
		} catch (ParamException e) {
			map.put("resultCode", e.getErrorCode());
			map.put("resultMessage", e.getMessage());
			map.put("result", e.getMessage());
		}
		return map;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete(String ids) {
		Map<String, Object> map = new HashMap<>();
		try {
			saleChanceService.delete(ids);
			map.put("resultCode", 1);
			map.put("resultMessage", "删除成功");
			map.put("result", "删除成功");
		} catch (ParamException e) {
			map.put("resultCode", e.getErrorCode());
			map.put("resultMessage", e.getMessage());
			map.put("result", e.getMessage());
		}
		return map;
	}
}
