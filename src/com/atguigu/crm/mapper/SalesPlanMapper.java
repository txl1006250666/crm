package com.atguigu.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.SalesPlan;
import com.atguigu.crm.entity.User;

public interface SalesPlanMapper {

	void save(SalesPlan plan);

	void update(SalesPlan plan);

	void deleteById(@Param("id") Long id);

	void saveResult(@Param("id") Long id, @Param("result") String result);


//	List<SalesChance> getContent(@Param("designee") User designee, @Param("fromIndex") int fromIndex, @Param("endIndex") int endIndex);

}
