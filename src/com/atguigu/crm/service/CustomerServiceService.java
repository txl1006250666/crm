package com.atguigu.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.entity.CustomerService;
import com.atguigu.crm.mapper.CustomerServiceMapper;

@Service
public class CustomerServiceService extends BaseService<CustomerService> {
	
	@Autowired
	private CustomerServiceMapper customerServiceMapper;

	public void save(CustomerService customerService) {
		customerServiceMapper.save(customerService);
	}

	public void allot(CustomerService cService) {
		customerServiceMapper.allot(cService);
	}

	public void delete(Long id) {
		customerServiceMapper.delete(id);
	}

	public CustomerService get(Long id) {
		return customerServiceMapper.get(id);
	}
	
}
