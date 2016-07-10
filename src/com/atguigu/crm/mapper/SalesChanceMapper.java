package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Contact;
import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;

public interface SalesChanceMapper {

//	List<SalesChance> getContent(Map<String, Object> map, @Param("createBy") User createBy, @Param("status") int status, 
//			@Param("fromIndex") int fromIndex, @Param("endIndex") int endIndex);
	List<SalesChance> getContent(Map<String, Object> map);

//	long getTotalElements(@Param("createBy") User createBy, @Param("status") int status);
	long getTotalElements(Map<String, Object> map);

	void save(SalesChance salesChance);

	void delete(@Param("id") Integer id);

	SalesChance getById(@Param("id") Long id);

	void update(SalesChance chance);

	Set<User> getAllUsers();

	void dipatch(SalesChance chance);

	void stop(Integer id);

	void updateFinish(SalesChance chance);



}
