package com.atguigu.crm.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Order;
import com.atguigu.crm.entity.OrderItem;
import com.atguigu.crm.service.OrderService;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;

@RequestMapping("/order")
@Controller
public class OrderHandler extends BaseHanlder {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/details/{id}")
	public String toDetailsUI(@PathVariable("id") Long id,
			Map<String,Object> map){
		
		Order order = orderService.get(id);
		map.put("order", order);
		
		return "order/details";
	}
	
	/**
	 * 此方法用于分页
	 */
	@RequestMapping("list/{id}")
	public String list(@PathVariable("id") Long id,
			@RequestParam(value="pageNo",required=false) String pageNoStr,
			Map<String, Object> map,
			HttpServletRequest request){
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataProcessUtils.transformMapToString(params);
		//初始化页码
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		params.put("EQL_id", id);
		
		Page<Order> page = orderService.getPages(params,pageNo);
		
		map.put("page", page);
		map.put("queryString", queryString);
		//map.put("size", size);
		
		return "order/list";
	}

}
