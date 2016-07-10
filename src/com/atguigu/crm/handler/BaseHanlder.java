package com.atguigu.crm.handler;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.service.BaseService;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;

public class BaseHanlder<T> {
	
	/*@Autowired
	protected BaseService<T> service;*/

	public void list(BaseService<T> service, String pageNoStr, Map<String, Object> map, ServletRequest request, String key, Object value) {

		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataProcessUtils.transformMapToString(params);
		
		params.put(key, value);
		
		// 初始化页码
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}

		Page<T> page = service.getPages(params, pageNo);

		map.put("page", page);
		map.put("queryString", queryString);

	}

}
