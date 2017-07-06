package com.shsxt.task;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.shsxt.service.CustomerService;

@Component
public class CustomerLossJob {
	
	@Resource
	private CustomerService customerService;

	@Scheduled(cron="0 0 2 * * ?")
	public void work() {
		customerService.runCustomerLoss();
	}
}
