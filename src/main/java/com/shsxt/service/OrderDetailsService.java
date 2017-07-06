package com.shsxt.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.base.AssertUtil;
import com.shsxt.base.BaseQuery;
import com.shsxt.dao.OrderDetailsDao;
import com.shsxt.model.OrderDetails;

@Service
public class OrderDetailsService {

	@Resource
	private OrderDetailsDao orderDetailsDao;

	public Integer count(Integer orderId) {
		AssertUtil.intIsEmpty(orderId, "请选择订单");
		Integer result = orderDetailsDao.count(orderId);
		return result;
	}

	public Map<String, Object> selectForPage(Integer orderId, BaseQuery query) {
		AssertUtil.intIsEmpty(orderId, "请选择订单");
		PageList<OrderDetails> orderDetails = orderDetailsDao.selectForPage(orderId,query.buildPageBounds());
		Map<String, Object> result = new HashMap<>();
		result.put("rows", orderDetails);
		result.put("total", orderDetails.getPaginator().getTotalCount());
		return result;
	}
	
}
