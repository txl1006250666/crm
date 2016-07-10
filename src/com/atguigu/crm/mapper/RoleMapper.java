package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Role;

public interface RoleMapper {

	long getTotalElements(Map<String, Object> mybatisParams);

	List<Role> getContent(Map<String, Object> mybatisParams);

	void save(Role role);

	void delete(@Param("id") Long id);

	Role get(@Param("id") Long id);

	List<Role> getAllRoles();

	void deleteAllAuthorities(Long id);

	void batchSaveAuthorities(Role role);

}
