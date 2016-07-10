package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.User;

public interface UserMapper {

	User getByName(@Param("name") String name);

	Set<User> getAllUsers();

	long getTotalElements(Map<String, Object> mybatisParams);

	List<User> getContent(Map<String, Object> mybatisParams);

	User get(@Param("id") Long id);

	void delete(Long id);

	void update(User user);

	void save(User user);
	
}
