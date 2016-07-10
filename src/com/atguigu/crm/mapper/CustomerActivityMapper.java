package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.CustomerActivity;

public interface CustomerActivityMapper {

	long getTotalElements(Map<String, Object> mybatisParams);

	List<CustomerActivity> getContent(Map<String, Object> mybatisParams);

	CustomerActivity get(@Param("id") Long id);

	void save(CustomerActivity activity);

	void update(CustomerActivity activity);

	void delete(Long id);

}
