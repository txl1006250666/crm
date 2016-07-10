package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Contact;
import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.mapper.ContactsMapper;
import com.atguigu.crm.mapper.CustomersMapper;
import com.atguigu.crm.mapper.SalesChanceMapper;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;
import com.atguigu.crm.utils.PropertyFilter;

@Service
public class SalesChanceService {
	
	@Autowired
	private SalesChanceMapper salesChanceMapper;
	
	@Autowired
	private CustomersMapper customersMapper;
	
	@Autowired
	private ContactsMapper contactMapper;
	
	@Transactional(readOnly=true)
	public Page<SalesChance> getPages(Map<String, Object> params,int pageNo){
		
		Page<SalesChance> page = new Page<>();
		page.setPageNo(pageNo);
		
		List<PropertyFilter> filters = DataProcessUtils.transformHandlerParamsToPropertyFilters(params);
		Map<String, Object> mybatisParams = DataProcessUtils.transformPropertyFiltersToHandlerParams(filters);
		
		long totalElements = salesChanceMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//2. 获取当前页面的 List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<SalesChance> content = salesChanceMapper.getContent(mybatisParams);
		page.setContent(content);
		
		//3. 组装 Page 并返回
		return page;
	}
	
	/*@Transactional
	public Page<SalesChance> getPages(Map<String, Object> newMap,int pageNo) {
		
		Page<SalesChance> page = new Page<>();
		page.setPageNo(pageNo);
		
		
		int fromIndex = (page.getPageNo()-1)*page.getPageSize() + 1 ;
		int endIndex = page.getPageSize() + fromIndex;
		
		Map<String, Object> result = new HashMap<>();
		String custName = null;
		String title = null;
		String contact = null;
		
		if (newMap != null) {
			
			String[] s1 =  newMap.get("search_LIKE_custName");
			if (s1!=null && s1.length>0) {
				custName = s1[0];
			}
			
			String[] s2 =  newMap.get("search_LIKE_title");
			if (s2 != null && s2.length>0) {
				title = s2[0];
			}
			
			String[] s3 =  newMap.get("search_LIKE_contact");
			if (s3 != null && s3.length>0) {
				contact = s3[0];
			}
			
			//String title = newMap.get("search_LIKE_title")[0];
			//String contact = newMap.get("search_LIKE_contact")[0];
			
			result.put("custName", custName);
			result.put("title", title);
			result.put("contact", contact);
			
		}
		
		result.put("createBy", createBy);
		result.put("status", status);
		
		//获取所有元素
//		long totalElements = salesChanceMapper.getTotalElements(createBy,status);
		long totalElements = salesChanceMapper.getTotalElements(result);
		page.setTotalElements((int)totalElements);
		
		result.put("fromIndex", fromIndex);
		result.put("endIndex", endIndex);
		
//		List<SalesChance> content = salesChanceMapper.getContent(result,createBy,status,fromIndex,endIndex);
		List<SalesChance> content = salesChanceMapper.getContent(result);
		page.setContent(content);
		
		return page;
	}*/
	
	@Transactional
	public void save(SalesChance salesChance) {
		
		salesChance.setStatus(1);
		salesChanceMapper.save(salesChance);
		
	}
	
	@Transactional
	public void delete(Integer id) {
		
		salesChanceMapper.delete(id);
		
	}

	@Transactional(readOnly=true)
	public SalesChance getById(Long id) {
		return salesChanceMapper.getById(id);
	}
	@Transactional
	public void update(SalesChance chance) {
		salesChanceMapper.update(chance);
	}
	@Transactional
	public void dispatch(SalesChance chance) {
		salesChanceMapper.dipatch(chance);
	}
	@Transactional
	public void stop(Integer id) {
		salesChanceMapper.stop(id);
	}
	
	@Transactional
	public void finish(Long chanceId) {
		
		//首先获取chance中的信息
		SalesChance chance = salesChanceMapper.getById(chanceId);
		//在机会表中进行信息更新 status=3
		chance.setStatus(3);
		salesChanceMapper.updateFinish(chance);
		
		//将相关信息插入客户表：id,name，随机No，state为正常
		Customer customer = new Customer();
		customer.setName(chance.getCustName());
		customer.setState("正常");
		customer.setNo(UUID.randomUUID().toString());
		customersMapper.saveCustomers(customer);
		
		//将相关信息插入联系表：id,name,tel,customer_id
		Contact contact = new Contact();
		String custName = chance.getCustName();
		contact.setName(custName);
		String tel = chance.getContactTel();
		contact.setTel(tel);
		contact.setCustomer(customer);
		contactMapper.saveContacts(contact);
		
		
	}

}
