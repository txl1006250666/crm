package com.atguigu.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DictMapper {

	List<String> getRegions();

	List<String> getLevels();

	List<String> getSatisfies();

	List<String> getCredits();

	List<String> get(@Param("flag") String flag);

}
