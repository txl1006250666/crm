package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.CustomerActivity;
import com.atguigu.crm.mapper.CustomerActivityMapper;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;
import com.atguigu.crm.utils.PropertyFilter;

@Service
public class CustomerActivityService {

	@Autowired
	private CustomerActivityMapper customerActivityMapper;
	
	@Transactional(readOnly=true)
	public Page<CustomerActivity> getPages(Map<String, Object> params, int pageNo) {
		Page<CustomerActivity> page = new Page<>();
		page.setPageNo(pageNo);
		
		List<PropertyFilter> filters = DataProcessUtils.transformHandlerParamsToPropertyFilters(params);
		Map<String, Object> mybatisParams = DataProcessUtils.transformPropertyFiltersToHandlerParams(filters);
		
		long totalElements = customerActivityMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//2. 获取当前页面的 List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<CustomerActivity> content = customerActivityMapper.getContent(mybatisParams);
		page.setContent(content);
		
		//3. 组装 Page 并返回
		return page;
	}
	
	@Transactional(readOnly=true)
	public CustomerActivity get(Long id) {
		return customerActivityMapper.get(id);
	}
	
	@Transactional
	public void save(CustomerActivity activity) {
		customerActivityMapper.save(activity);
	}
	
	@Transactional
	public void update(CustomerActivity activity) {
		customerActivityMapper.update(activity);
	}
	
	@Transactional
	public void delete(Long id) {
		customerActivityMapper.delete(id);
	}
	
}
