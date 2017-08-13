package com.shsxt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.shsxt.base.AssertUtil;
import com.shsxt.dao.SaleChanceDao;
import com.shsxt.dto.SaleChanceQuery;
import com.shsxt.exception.ParamException;
import com.shsxt.model.SaleChance;

@Service
public class SaleChanceService {
	
	@Resource
	private SaleChanceDao saleChanceDao;
	private static Logger logger = LoggerFactory.getLogger(SaleChanceService.class);
	/**
	 * 查询列表
	 * @param query
	 * @return
	 */
	public Map<String, Object> selectForPage(SaleChanceQuery query) {
		//分页查询  构建一个PageBounds --->dao返回PageList--->sql
//		if (query.getPage() == null || query.getPage() < 1) {
//			query.setPage(1);
//		}
//		if (query.getRows() == null || query.getRows() < 1) {
//			query.setRows(10);
//		}
//		PageBounds pageBounds = new PageBounds(query.getPage(), query.getRows());
		PageList<SaleChance> saleChances = saleChanceDao.selectForPage(query,query.buildPageBounds());
		Paginator paginator = saleChances.getPaginator();//分页对象
		Map<String,Object> map = new HashMap<>();
		map.put("paginator", paginator);
		map.put("total", paginator.getTotalCount());
		map.put("rows", saleChances);
		return map;
	}

	/**
	 * 添加或修改
	 * @param saleChance
	 * @param userName
	 */
	public void addOrUpdate(SaleChance saleChance,String userName) {
		//基本参数验证
		AssertUtil.intIsEmpty(saleChance.getCustomerId(), "请选择客户");
		AssertUtil.intIsEmpty(saleChance.getCgjl(), "请输入成功机率");
//		if (saleChance.getCustomerId() == null || saleChance.getCustomerId() < 1) {
//			throw new ParamException("请选择客户");
//		}
//		if (saleChance.getCgjl() == null || saleChance.getCgjl() < 1) {
//			throw new ParamException("请输入成功几率");
//		}
		
		saleChance.setCreateMan(userName);
		Integer id = saleChance.getId();
		SaleChance saleChanceFromDB = null;
		
		if (id != null){//验证该记录是否存在
			saleChanceFromDB = saleChanceDao.findById(id);
			AssertUtil.objectIsEmpty(saleChanceFromDB, "该记录不存在");
//			if (saleChanceFromDB == null) {
//				throw new ParamException("该记录不存在");
//			}
		}
		//是否有过分配
		String assignMan = saleChance.getAssignMan();
		if (StringUtils.isNoneBlank(assignMan)) {
			if (id != null) {
				if (!saleChance.getAssignMan().equals(saleChanceFromDB.getAssignMan())) {
					saleChance.setAssignTime(new Date());
				} else {
					saleChance.setAssignTime(saleChanceFromDB.getAssignTime());
				}
			} else {
				saleChance.setAssignTime(new Date());
			}
			saleChance.setState(1);
		} else {
			saleChance.setState(0);
		}
		saleChance.setDevResult(0);
		
		if (id == null) {//新增
			saleChance.setCreateDate(new Date());
			saleChance.setUpdateDate(new Date());
			saleChance.setIsValid(1);
			saleChanceDao.insert(saleChance);
			logger.info("插入返回主键：{}",saleChance.getId());
		} else {//修改
			saleChanceDao.update(saleChance);
		}
	}

	/**
	 * 删除
	 * @param ids
	 */
	public void delete(String ids) {
		
		if (StringUtils.isBlank(ids)) {
			throw new ParamException("请选择记录进行删除");
		}
		saleChanceDao.delete(ids);
	}

	public SaleChance findById(Integer saleChanceId) {
		AssertUtil.intIsEmpty(saleChanceId, "请选择营销机会");
		SaleChance saleChance = saleChanceDao.findById(saleChanceId);
		AssertUtil.objectIsEmpty(saleChance, "该机会不存在");
		return saleChance;
	}

	/**
	 * 更新开发状态
	 * @param saleChanceId
	 * @param devResult
	 */
	public void updateDevResult(Integer saleChanceId, int devResult) {
		
		AssertUtil.intIsEmpty(saleChanceId, "请选择营销机会");
		
		saleChanceDao.updateDevResult(saleChanceId,devResult);
		
	}
}
