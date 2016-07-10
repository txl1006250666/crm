package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Order;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.mapper.UserMapper;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;
import com.atguigu.crm.utils.PropertyFilter;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Transactional(readOnly=true)
	public User login(String name,String password){
		
		User user = userMapper.getByName(name);
		
		if (user != null && user.getEnabled() == 1 && password.equals(user.getPassword())) {
			return user;
		}
		
		return null;
	}
	
	@Transactional(readOnly=true)
	public Set<User> getAllUsers() {
		return userMapper.getAllUsers();
	}
	
	@Transactional(readOnly=true)
	public Page<User> getPages(Map<String, Object> params, int pageNo) {
		Page<User> page = new Page<>();
		page.setPageNo(pageNo);
		
		List<PropertyFilter> filters = DataProcessUtils.transformHandlerParamsToPropertyFilters(params);
		Map<String, Object> mybatisParams = DataProcessUtils.transformPropertyFiltersToHandlerParams(filters);
		
		long totalElements = userMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//2. 获取当前页面的 List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<User> content = userMapper.getContent(mybatisParams);
		page.setContent(content);
		
		//3. 组装 Page 并返回
		return page;
	}
	
	@Transactional(readOnly=true)
	public User get(Long id) {
		return userMapper.get(id);
	}
	
	@Transactional
	public void delete(Long id) {
		userMapper.delete(id);
	}
	
	@Transactional
	public void update(User user) {
		userMapper.update(user);
	}
	
	@Transactional
	public void save(User user) {
		userMapper.save(user);
	}
}
