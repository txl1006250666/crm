package com.atguigu.crm.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.mapper.ConsistMapper;
import com.atguigu.crm.service.ConsistService;
import com.atguigu.crm.service.ReportService;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;

@RequestMapping("/report")
@Controller
public class ReportHandler {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ConsistService consistService;
	
	/**
	 * 此方法用于跳转到list页面
	 */
	@RequestMapping(value="/consist/list")
	public String listConsist(@RequestParam(value="pageNo",required=false) String pageNoStr,
			Map<String, Object> map,
			HttpServletRequest request){
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataProcessUtils.transformMapToString(params);
		
		//初始化页码
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		if (params.size() == 0) {
			params.put("EQS_type", "level");
		}
		
		Page<Map<String, String>> page = consistService.getPages(params,pageNo);
		
		/*List<Object[]> newList = new ArrayList<>();
		
		 List<Map<String, String>> contents = page.getContent();
		 StringBuilder sb = new StringBuilder();
		 Object[] obj = new Object[3];
		 
		 for (Map<String, String> content : contents) {
			for (Map.Entry<String, String> entry : content.entrySet()) {
				sb.append(entry.getValue()).append(",");
			}
		 }
		 
		 obj = sb.substring(0, sb.length()-1).toString().split(",");
		 
		 newList.add(obj);
		 
		 page.setContent(newList);*/
		/*for (Object[] objects : contents) {
			Object object = objects[0];
			System.out.println("-------------");
		}*/
		
		String type = (String) params.get("EQS_type");
		
		map.put("page", page);
		map.put("type", type);
		map.put("queryString", queryString);
		
		return "report/consist";
	}
	
	
	/**
	 * 此方法用于跳转到list页面
	 */
	@RequestMapping(value="/distribution/list")
	public String list(@RequestParam(value="pageNo",required=false) String pageNoStr,
			Map<String, Object> map,
			HttpServletRequest request){
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataProcessUtils.transformMapToString(params);
		
		//初始化页码
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		Page<Map<String, Object>> page = reportService.getPages(params,pageNo);
		
		
		map.put("page", page);
		map.put("queryString", queryString);
		
		return "report/pay";
	}

}
