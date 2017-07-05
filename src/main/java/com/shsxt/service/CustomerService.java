package com.shsxt.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.base.AssertUtil;
import com.shsxt.dao.CustomerDao;
import com.shsxt.dao.CustomerLossDao;
import com.shsxt.dao.OrderDao;
import com.shsxt.dto.CustomerQuery;
import com.shsxt.model.Customer;
import com.shsxt.model.CustomerLoss;
import com.shsxt.vo.CustomerVo;

@Service
public class CustomerService {

	@Resource
	private CustomerDao customerDao;
	@Resource
	private CustomerLossDao customerLossDao;
	@Resource
	private OrderDao orderDao;

	public List<CustomerVo> findAll() {
		
		return customerDao.findAll();
	}

	public Map<String, Object> selectForPage(CustomerQuery customerQuery) {
		PageList<Customer> customers = customerDao.selectForPage(customerQuery,customerQuery.buildPageBounds());
		Map<String, Object> map = new HashMap<>();
		map.put("rows", customers);
		map.put("total", customers.getPaginator().getTotalCount());
		return map;
	}

	public Customer findById(Integer cusId) {
		AssertUtil.intIsEmpty(cusId, "请选择一条记录");
		Customer customer = customerDao.findById(cusId);
		return customer;
	}

	/**
	 * 定时抓取流失客户
	 */
	public void runCustomerLoss() {
		
		//六个月没有下个单的客户
		List<CustomerLoss> customerLosses = new ArrayList<>();
		List<Customer> customers = customerDao.findLossCustomer();
		List<Integer> customerIds = buildCustomerLoss(customers,customerLosses,0);
		
		//六个月前可能下过单
		List<Customer> customerNoOrderLongTimes = customerDao.findLossCustomerNoOrderLongTime();
		List<Integer> customerMoreIds = buildCustomerLoss(customerNoOrderLongTimes, customerLosses, 1);
		customerIds.addAll(customerMoreIds);
		
		//插入数据
		if (customerLosses != null && !customerLosses.isEmpty()) {
			customerLossDao.insertBatch(customerLosses);
		}
		
		//更新客户的状态
		if (customerIds != null && !customerIds.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			for (Integer customerId : customerIds) {
				sb.append(customerId).append(",");
			}
			customerDao.updateStates(sb.substring(0,sb.length()-1));
		}
	}

	/**
	 * 构建
	 * @param customers
	 * @param customerLosses
	 * @param type
	 * @return
	 */
	private List<Integer> buildCustomerLoss(List<Customer> customers, List<CustomerLoss> customerLosses, int type) {
		if (customers == null || customers.isEmpty()) {
			return Collections.emptyList();
		}
		List<Integer> customerIds = new ArrayList<>();
		for (Customer customer : customers) {
			CustomerLoss customerLoss = new CustomerLoss();
			customerLoss.setCreateDate(new Date());
			customerLoss.setCusManager(customer.getCusManager());
			customerLoss.setCusName(customer.getName());
			customerLoss.setCusNo(customer.getKhno());
			customerLoss.setIsValid(1);
			if (type == 1) {//需要查询最后一次订单时间
				Date lastOrderTime = orderDao.findCustomerOrderDate(customer.getId());
				customerLoss.setLastOrderTime(lastOrderTime);
			}
			customerLosses.add(customerLoss);
			customerIds.add(customer.getId());
		}
		return customerIds;
	}
}
