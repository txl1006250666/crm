package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Contact;
import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.mapper.CustomersMapper;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;
import com.atguigu.crm.utils.PropertyFilter;

@Service
public class CustomerService {
	
	@Autowired
	private CustomersMapper customersMapper;
	
	@Transactional(readOnly=true)
	public Customer get(Long id) {
		return customersMapper.get(id);
	}
	
	@Transactional(readOnly=true)
	public Page<Customer> getPages(Map<String, Object> params, int pageNo) {
		Page<Customer> page = new Page<>();
		page.setPageNo(pageNo);
		
		List<PropertyFilter> filters = DataProcessUtils.transformHandlerParamsToPropertyFilters(params);
		Map<String, Object> mybatisParams = DataProcessUtils.transformPropertyFiltersToHandlerParams(filters);
		
		long totalElements = customersMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//2. 获取当前页面的 List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<Customer> content = customersMapper.getContent(mybatisParams);
		page.setContent(content);
		
		//3. 组装 Page 并返回
		return page;
	}

	@Transactional(readOnly=true)
	public List<Contact> getManagers(Long id) {
		return customersMapper.getManagers(id);
	}
	
	@Transactional
	public void update(Customer customer) {
		customersMapper.update(customer);
	}

	public void deleteByUpdate(Long id) {
		customersMapper.deleteByUpdate(id);
	}

	public void updateStatusByDrain(Long id) {
		customersMapper.updateStatusByDrain(id);
	}

	public List<Customer> getAllForList() {
		return customersMapper.getAllForList();
	}
	
}
