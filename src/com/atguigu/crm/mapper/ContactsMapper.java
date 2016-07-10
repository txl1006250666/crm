package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Contact;
import com.atguigu.crm.entity.Customer;

public interface ContactsMapper {

	void saveContacts(Contact contact);

	long getTotalElements(Map<String, Object> mybatisParams);

	List<Customer> getContent(Map<String, Object> mybatisParams);

	Contact get(@Param("id") Long id);

	void update(Contact contact);

	void delete(Long id);

}
