package com.atguigu.crm.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Contact;
import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.CustomerActivity;
import com.atguigu.crm.service.CustomerActivityService;
import com.atguigu.crm.service.CustomerService;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;

@Controller
@RequestMapping("/activity")
public class CustomerActivityHandler extends BaseHanlder {

	@Autowired
	private CustomerActivityService customerActivityService;
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 此方法用于删除
	 */
	@RequestMapping("delete/{id}/{customerid}")
	public String delete(@PathVariable("id") Long id,
			@PathVariable("customerid") Long customerid,
			RedirectAttributes attributes){
		
		//Long id = Long.parseLong(idStr);
		//Long customerid = Long.parseLong(customeridStr);
		
		CustomerActivity activity = customerActivityService.get(id);
		activity.setCustomer(new Customer());
		//先将本contact的customerId设为null
		customerActivityService.update(activity);
		
		customerActivityService.delete(id);
		
		return "redirect:/activity/list/" + customerid;
	}
	
	/**
	 * 此方法用于修改
	 */
	@RequestMapping(value="/create",method=RequestMethod.PUT)
	public String update(CustomerActivity activity){
		
		customerActivityService.update(activity);
		return "redirect:/activity/list/" + activity.getCustomer().getId(); //id为customer.id
	}
	
	
	/**
	 * 此方法用于新建
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String save(CustomerActivity activity){
		
		customerActivityService.save(activity);
		return "redirect:/activity/list/" + activity.getCustomer().getId(); //id为customer.id
	}
	
	/**
	 * 此方法用于跳转修改页面
	 */
	@RequestMapping("/create/{id}")
	public String toEditUI(@PathVariable("id") Long id,Map<String,Object> map){
		
		CustomerActivity activity = customerActivityService.get(id);
		
		map.put("activity", activity);
		return "activity/input";
	}
	
	
	/**
	 * 此方法用于跳转到新建页面
	 */
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String toInputUI(@RequestParam("customerid") Long customerid,
			Map<String,Object> map){
		CustomerActivity activity = new CustomerActivity();
		map.put("customerid", customerid);
		map.put("activity", activity);
		return "activity/input";
	}
	
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
		
		Page<CustomerActivity> page = customerActivityService.getPages(params,pageNo);
		
		//放入另两个参数
		Customer customer = customerService.get(id);
		//int size = page.getContent().size();
		map.put("page", page);
		map.put("customer", customer);
		map.put("queryString", queryString);
		//map.put("size", size);
		
		return "activity/list";
	}
}
