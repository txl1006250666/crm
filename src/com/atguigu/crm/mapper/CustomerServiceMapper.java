package com.atguigu.crm.mapper;

import java.sql.Date;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.CustomerService;

public interface CustomerServiceMapper extends BaseMapper<CustomerService> {

	void save(CustomerService customerService);

	void allot(CustomerService cService);

	void delete(@Param("id") Long id);

	CustomerService get(@Param("id") Long id);

}
