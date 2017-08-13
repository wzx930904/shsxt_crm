package com.shsxt.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.dto.CustomerQuery;
import com.shsxt.service.CustomerService;
import com.shsxt.vo.CustomerVo;


@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController{
	
	@Resource
	private CustomerService customerService;
	
	@RequestMapping("find_all")
	@ResponseBody
	public List<CustomerVo> findAll() {
		List<CustomerVo> customerVos = customerService.findAll();
		return customerVos;
	}
	
	@RequestMapping("index")
	public String index() {
		return "customer";
	}

	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> selectForPage(CustomerQuery customerQuery){
		Map<String, Object> result = customerService.selectForPage(customerQuery);
		return result;
	}
	
	
}
