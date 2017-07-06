package com.shsxt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shsxt.base.AssertUtil;
import com.shsxt.dao.CustomerReprieveDao;
import com.shsxt.model.CustomerReprieve;


@Service
public class CustomerReprieveService {

	@Resource
	private CustomerReprieveDao customerReprieveDao;

	public Map<String, Object> findAll(Integer lossId) {
		AssertUtil.intIsEmpty(lossId, "请选择流失客户");
		List<CustomerReprieve> customerReprieves = customerReprieveDao.findAll(lossId);
		Map<String, Object> map = new HashMap<>();
		map.put("rows", customerReprieves);
		return map;
	}

	public void add(Integer lossId, String measure) {
		AssertUtil.intIsEmpty(lossId, "请选择流失客户");
		AssertUtil.stringIsEmpty(measure, "请输入暂缓措施");
		customerReprieveDao.add(lossId,measure);
	}

	public void update(Integer id, String measure) {
		AssertUtil.intIsEmpty(id, "请选择记录更新");
		AssertUtil.stringIsEmpty(measure, "请输入暂缓措施");
		customerReprieveDao.update(id,measure);
	}

	public void delete(Integer id) {
		AssertUtil.intIsEmpty(id, "请选择记录进行删除");
		customerReprieveDao.delete(id);
	}
	
	
}
