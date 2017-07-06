package com.shsxt.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.base.Constant;
import com.shsxt.base.ResultInfo;
import com.shsxt.dto.CustomerLossQuery;
import com.shsxt.dto.CustomerQuery;
import com.shsxt.service.CustomerLossService;


@Controller
@RequestMapping("customer_loss")
public class CustomerLossController extends BaseController{

	@Resource
	private CustomerLossService customerLossService;
	
	@RequestMapping("index")
	public String index() {
		return "customer_loss";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> selectForPage(CustomerLossQuery query) {
		Map<String, Object> result = customerLossService.selectForPage(query);
		return result;
	}
	
	@RequestMapping("confirmLoss")
	@ResponseBody
	public ResultInfo confirmLoss(Integer lossId,String lossReason) {
		customerLossService.confirmLoss(lossId,lossReason);
		return success(Constant.SUCCESS_MSG);
	}
}
