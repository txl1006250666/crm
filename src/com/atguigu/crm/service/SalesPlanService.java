package com.atguigu.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.SalesPlan;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.mapper.SalesPlanMapper;
import com.atguigu.crm.utils.Page;

@Service
public class SalesPlanService {
	
	@Autowired
	private SalesPlanMapper salesPlanMapper;
	@Transactional
	public void save(SalesPlan plan) {
		salesPlanMapper.save(plan);
	}
	@Transactional
	public void update(SalesPlan plan) {
		salesPlanMapper.update(plan);
	}
	@Transactional
	public void deleteById(Long id) {
		salesPlanMapper.deleteById(id);
	}
	@Transactional
	public void saveResult(Long id, String result) {
		salesPlanMapper.saveResult(id,result);
	}
	
/*	public Page<SalesChance> getPages(User designee, int pageNo) {
		
		Page<SalesChance> page = new Page<>();
		page.setPageNo(pageNo);
		
		int fromIndex = (page.getPageNo()-1)*page.getPageSize() + 1 ;
		int endIndex = page.getPageSize() + fromIndex;
		
		List<SalesChance> content = salesPlanMapper.getContent(designee,fromIndex,endIndex);
		page.setContent(content);
		
		return page;
	}
*/	
}
