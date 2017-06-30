package com.shsxt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shsxt.base.AssertUtil;
import com.shsxt.dao.CusDevPlanDao;
import com.shsxt.model.CusDevPlan;

import constant.DevResult;

@Service
public class CusDevPlanService {
	
	@Resource
	private CusDevPlanDao cusDevPlanDao;
	@Resource
	private SaleChanceService saleChanceService;
	
	/**
	 * 查询营销机会计划项
	 * @param saleChanceId
	 * @return
	 */
	public Map<String, Object> findSaleChancePlans(Integer saleChanceId) {
		AssertUtil.intIsEmpty(saleChanceId, "请选择营销机会");
		List<CusDevPlan> cusDevPlans = cusDevPlanDao.findSaleChancePlans(saleChanceId);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", cusDevPlans);
		return result;
	}

	/**
	 * 新增
	 * @param cusDevPlan
	 */
	public void save(CusDevPlan cusDevPlan) {
		String planItem = cusDevPlan.getPlanItem();
		AssertUtil.stringIsEmpty(planItem, "请输入计划内容");
		Date planDate = cusDevPlan.getPlanDate();
		AssertUtil.objectIsEmpty(planDate, "请选择日期");
		String exeAffect = cusDevPlan.getExeAffect();
		AssertUtil.stringIsEmpty(exeAffect, "请输入执行效果");
		cusDevPlan.setCreateDate(new Date());
		cusDevPlan.setUpdateDate(new Date());
		cusDevPlan.setIsValid(1);
		cusDevPlanDao.insert(cusDevPlan);
		//修改营销机会开发状态
		saleChanceService.updateDevResult(cusDevPlan.getSaleChanceId(),DevResult.DEVING.getDevResult());
	}

	/**
	 * 修改
	 * @param cusDevPlan
	 */
	public void update(CusDevPlan cusDevPlan) {
		Integer id = cusDevPlan.getId();
		AssertUtil.intIsEmpty(id, "请选择记录");
		String planItem = cusDevPlan.getPlanItem();
		AssertUtil.stringIsEmpty(planItem, "请输入计划内容");
		Date planDate = cusDevPlan.getPlanDate();
		AssertUtil.objectIsEmpty(planDate, "请选择日期");
		String exeAffect = cusDevPlan.getExeAffect();
		AssertUtil.stringIsEmpty(exeAffect, "请输入执行效果");
		cusDevPlan.setUpdateDate(new Date());
		cusDevPlanDao.update(cusDevPlan);
	}
	
	public void delete(Integer id) {
		AssertUtil.intIsEmpty(id, "请选择记录");
		cusDevPlanDao.delete(id);
	}
}
