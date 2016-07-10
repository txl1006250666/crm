package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.CustomerActivity;
import com.atguigu.crm.entity.Order;
import com.atguigu.crm.mapper.OrderMapper;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;
import com.atguigu.crm.utils.PropertyFilter;

@Service
public class OrderService {
	
	@Autowired
	private OrderMapper orderMapper;

	@Transactional(readOnly=true)
	public Page<Order> getPages(Map<String, Object> params, int pageNo) {
		Page<Order> page = new Page<>();
		page.setPageNo(pageNo);
		
		List<PropertyFilter> filters = DataProcessUtils.transformHandlerParamsToPropertyFilters(params);
		Map<String, Object> mybatisParams = DataProcessUtils.transformPropertyFiltersToHandlerParams(filters);
		
		long totalElements = orderMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//2. 获取当前页面的 List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<Order> content = orderMapper.getContent(mybatisParams);
		page.setContent(content);
		
		//3. 组装 Page 并返回
		return page;
	}
	
	@Transactional(readOnly=true)
	public Order get(Long id) {
		return orderMapper.get(id);
	}

}
