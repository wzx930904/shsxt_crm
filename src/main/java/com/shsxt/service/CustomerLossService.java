package com.shsxt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.base.AssertUtil;
import com.shsxt.dao.CustomerDao;
import com.shsxt.dao.CustomerLossDao;
import com.shsxt.dto.CustomerLossQuery;
import com.shsxt.dto.CustomerQuery;
import com.shsxt.model.CustomerLoss;

@Service
public class CustomerLossService {

	@Resource
	private CustomerLossDao customerLossDao;
	@Resource
	private CustomerDao customerDao;

	public  Map<String, Object> selectForPage(CustomerLossQuery query) {
		PageList<CustomerLoss> customerLosses = customerLossDao.selectForPage(query,query.buildPageBounds());
		Map<String, Object> result = new HashMap<>();
		result.put("rows", customerLosses);
		result.put("total", customerLosses.getPaginator().getTotalCount());
		return result;
	}

	public CustomerLoss loadById(Integer lossId) {
		AssertUtil.intIsEmpty(lossId, "请选择流失客户");
		CustomerLoss customerLoss = customerLossDao.loadById(lossId);
		return customerLoss;
	}

	public void confirmLoss(Integer lossId, String lossReason) {
		AssertUtil.intIsEmpty(lossId, "请选择流失客户");
		AssertUtil.stringIsEmpty(lossReason, "请输入流失原因");
		CustomerLoss customerLoss = customerLossDao.loadById(lossId);
		AssertUtil.objectIsEmpty(customerLoss, "该客户并没有流失，请确定");
		customerLoss.setLossReason(lossReason);
		customerLoss.setState(1);
		customerLoss.setUpdateDate(new Date());
		customerDao.updateLossState(customerLoss.getCusNo());
	}
}
