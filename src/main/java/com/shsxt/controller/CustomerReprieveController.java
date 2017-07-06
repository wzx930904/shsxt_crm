package com.shsxt.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.base.ResultInfo;
import com.shsxt.model.CustomerLoss;
import com.shsxt.service.CustomerLossService;
import com.shsxt.service.CustomerReprieveService;

@Controller
@RequestMapping("customer_reprieve")
public class CustomerReprieveController extends BaseController{
	
	@Resource
	private CustomerReprieveService customerReprieveService;
	@Resource
	private CustomerLossService customerLossService;

	@RequestMapping("index")
	public String index(Integer lossId,Model model) {
		CustomerLoss customerLoss = customerLossService.loadById(lossId);
		model.addAttribute("customerLoss", customerLoss);
		return "customer_reprieve";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> list(Integer lossId) {
		Map<String, Object> result = customerReprieveService.findAll(lossId);
		return result;
	}
	
	@RequestMapping("add")
	@ResponseBody
	public ResultInfo add (Integer lossId,String measure) {
		customerReprieveService.add(lossId,measure);
		return success("添加成功");
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ResultInfo update(Integer id,String measure) {
		customerReprieveService.update(id,measure);
		return success("修改成功");
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ResultInfo delete(Integer id) {
		customerReprieveService.delete(id);
		return success("删除成功");
	}
}
