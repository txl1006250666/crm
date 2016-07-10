package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Contact;
import com.atguigu.crm.entity.Customer;

public interface CustomersMapper {
	
	void update(Customer customer);

	Customer get(@Param("id") Long id);

	long getTotalElements(Map<String, Object> mybatisParams);

	List<Customer> getContent(Map<String, Object> mybatisParams);

	List<Contact> getManagers(Long id);
	
	void saveCustomers(Customer customer);

	void deleteByUpdate(@Param("id") Long id);

	void updateStatusByDrain(@Param("id") Long id);

	List<Customer> getAllForList();
}
