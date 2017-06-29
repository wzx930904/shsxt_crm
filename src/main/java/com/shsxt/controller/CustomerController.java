package com.shsxt.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
