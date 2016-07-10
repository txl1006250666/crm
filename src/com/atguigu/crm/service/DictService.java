package com.atguigu.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.mapper.DictMapper;

@Service
public class DictService {

	@Autowired
	private DictMapper dictMapper;
	
	@Transactional(readOnly=true)
	public List<String> getRegions() {
		return dictMapper.getRegions();
	}
	
	@Transactional(readOnly=true)
	public List<String> getLevels() {
		return dictMapper.getLevels();
	}
	
	@Transactional(readOnly=true)
	public List<String> getSatisfies() {
		return dictMapper.getSatisfies();
	}
	
	@Transactional(readOnly=true)
	public List<String> getCredits() {
		return dictMapper.getCredits();
	}
	
	@Transactional(readOnly=true)
	public List<String> get(String flag) {
		return dictMapper.get(flag);
	}

}
