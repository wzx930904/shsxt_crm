package com.shsxt.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shsxt.dao.CustomerDao;
import com.shsxt.vo.CustomerVo;

@Service
public class CustomerService {

	@Resource
	private CustomerDao customerDao;

	public List<CustomerVo> findAll() {
		
		return customerDao.findAll();
	}
}
