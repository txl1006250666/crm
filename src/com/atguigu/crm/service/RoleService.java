package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.Role;
import com.atguigu.crm.mapper.RoleMapper;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;
import com.atguigu.crm.utils.PropertyFilter;

@Service
public class RoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Transactional(readOnly=true)
	public Page<Role> getPages(Map<String, Object> params, int pageNo) {
		Page<Role> page = new Page<>();
		page.setPageNo(pageNo);
		
		List<PropertyFilter> filters = DataProcessUtils.transformHandlerParamsToPropertyFilters(params);
		Map<String, Object> mybatisParams = DataProcessUtils.transformPropertyFiltersToHandlerParams(filters);
		
		long totalElements = roleMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//2. 获取当前页面的 List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<Role> content = roleMapper.getContent(mybatisParams);
		page.setContent(content);
		
		//3. 组装 Page 并返回
		return page;
	}
	
	@Transactional
	public void save(Role role) {
		roleMapper.save(role);
	}
	
	@Transactional
	public void delete(Long id) {
		roleMapper.delete(id);
	}
	
	@Transactional(readOnly=true)
	public Role get(Long id) {
		return roleMapper.get(id);
	}
	
	@Transactional(readOnly=true)
	public List<Role> getAllRoles() {
		return roleMapper.getAllRoles();
	}
	
	@Transactional
	public void deleteAllAuthorities(Long id) {
		roleMapper.deleteAllAuthorities(id);
	}
	
	@Transactional
	public void batchSaveAuthorities(Role role) {
		roleMapper.batchSaveAuthorities(role);	
	}
	
	
}
