package com.shsxt.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.dto.ContactQuery;
import com.shsxt.model.Customer;
import com.shsxt.model.CustomerOrder;
import com.shsxt.service.CustomerService;
import com.shsxt.service.OrderService;

@Controller
@RequestMapping("order")
public class OrderController extends BaseController{
	
	@Resource
	private CustomerService customerService;
	@Resource
	private OrderService orderService;

	@RequestMapping("index")
	public String index(Integer cusId,Model model) {
		model.addAttribute("cusId", cusId);
		Customer customer = customerService.findById(cusId);
		model.addAttribute("customer", customer);
		return "customer_order";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> selectForPage(ContactQuery query) {
		Map<String, Object> result = orderService.selectForPage(query);
		return result;
	}
	
	@RequestMapping("detail")
	@ResponseBody
	public CustomerOrder detail(Integer orderId) {
		CustomerOrder customerOrder = orderService.findById(orderId);
		return customerOrder;
	}
}
