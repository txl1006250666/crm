package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.atguigu.crm.entity.CustomerDrain;

public interface CustomerDrainMapper {

	@Update("{call check_drain()}")
	public void callCheckDrainProcedure();

	public long getTotalElements(Map<String, Object> mybatisParams);

	public List<CustomerDrain> getContent(Map<String, Object> mybatisParams);

	public CustomerDrain get(@Param("id") Long id);

	public void update(CustomerDrain drain);
	
}
