package com.shsxt.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.base.ResultInfo;
import com.shsxt.model.CusDevPlan;
import com.shsxt.model.SaleChance;
import com.shsxt.service.CusDevPlanService;
import com.shsxt.service.SaleChanceService;

@Controller
@RequestMapping("cus_dev_plan")
public class CusDevPlanController extends BaseController{

	@Resource
	private SaleChanceService saleChanceService;
	@Resource
	private CusDevPlanService cusDevPlanService;
	
	@RequestMapping("index")
	public String index(Model model,Integer saleChanceId,Integer show) {
		model.addAttribute("show", show);
		SaleChance saleChance = saleChanceService.findById(saleChanceId);
		model.addAttribute("saleChance", saleChance);
		return "cus_dev_plan";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> list(Integer saleChanceId) {
		Map<String, Object> result = cusDevPlanService.findSaleChancePlans(saleChanceId);
		return result;
	}
	
	@RequestMapping("add")
	@ResponseBody
	public ResultInfo add(CusDevPlan cusDevPlan) {
		cusDevPlanService.save(cusDevPlan);
		return success("添加成功");
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ResultInfo update(CusDevPlan cusDevPlan) {
		cusDevPlanService.update(cusDevPlan);
		return success("修改成功");
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ResultInfo delete(Integer id) {
		cusDevPlanService.delete(id);
		return success("删除成功");
	}
	
	@RequestMapping("update_dev_result")
	@ResponseBody
	public ResultInfo updateDevResult (Integer saleChanceId,Integer devResult) {
		saleChanceService.updateDevResult(saleChanceId, devResult);
		return success("更新成功");
	}
}
