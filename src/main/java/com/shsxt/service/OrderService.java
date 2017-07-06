package com.shsxt.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.base.AssertUtil;
import com.shsxt.base.BaseQuery;
import com.shsxt.dao.OrderDao;
import com.shsxt.dto.ContactQuery;
import com.shsxt.model.CustomerOrder;

@Service
public class OrderService {

	@Resource
	private OrderDao orderDao;

	public Map<String, Object> selectForPage(ContactQuery query) {
		AssertUtil.intIsEmpty(query.getCusId(), "请选择一条记录");
		PageList<CustomerOrder> customerOrders = orderDao.selectForPage(query.getCusId(),query.buildPageBounds());
		Map<String, Object> result = new HashMap<>();
		result.put("rows", customerOrders);
		result.put("total", customerOrders.getPaginator().getTotalCount());
		return result;
	}

	public CustomerOrder findById(Integer orderId) {
		AssertUtil.intIsEmpty(orderId, "请选择订单");
		CustomerOrder customerOrder = orderDao.findById(orderId);
		return customerOrder;
	}
	
}
