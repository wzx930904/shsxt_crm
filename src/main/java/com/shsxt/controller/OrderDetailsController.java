package com.shsxt.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.base.BaseQuery;
import com.shsxt.base.ResultInfo;
import com.shsxt.service.OrderDetailsService;

@Controller
@RequestMapping("order_details")
public class OrderDetailsController extends BaseController{
	
	@Resource
	private OrderDetailsService orderDetailsService;

	@RequestMapping("getTotalPrice")
	@ResponseBody
	public ResultInfo getTotalPrice(Integer orderId) {
		Integer result = orderDetailsService.count(orderId);
		return success(result);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> selectForPage(Integer orderId,BaseQuery query) {
		Map<String, Object> result = orderDetailsService.selectForPage(orderId,query);
		return result;
	}
}
