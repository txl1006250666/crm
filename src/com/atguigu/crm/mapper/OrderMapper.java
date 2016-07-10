package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Order;

public interface OrderMapper {

	long getTotalElements(Map<String, Object> mybatisParams);

	List<Order> getContent(Map<String, Object> mybatisParams);

	Order get(@Param("id") Long id);

}
