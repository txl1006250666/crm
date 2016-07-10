package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.Role;
import com.atguigu.crm.mapper.BaseMapper;
import com.atguigu.crm.mapper.RoleMapper;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;
import com.atguigu.crm.utils.PropertyFilter;

public class BaseService<T> {
	
	@Autowired
	private BaseMapper<T> baseMapper;
	
	@Transactional(readOnly=true)
	public Page<T> getPages(Map<String, Object> params, int pageNo) {
		Page<T> page = new Page<>();
		page.setPageNo(pageNo);
		
		List<PropertyFilter> filters = DataProcessUtils.transformHandlerParamsToPropertyFilters(params);
		Map<String, Object> mybatisParams = DataProcessUtils.transformPropertyFiltersToHandlerParams(filters);
		
		long totalElements = baseMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//2. 获取当前页面的 List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<T> content = baseMapper.getContent(mybatisParams);
		page.setContent(content);
		
		//3. 组装 Page 并返回
		return page;
	}
	
}
	
