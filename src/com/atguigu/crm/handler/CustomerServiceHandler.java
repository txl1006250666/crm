package com.atguigu.crm.handler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.CustomerService;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.service.CustomerServiceService;
import com.atguigu.crm.service.DictService;
import com.atguigu.crm.service.UserService;

@RequestMapping("/service")
@Controller
public class CustomerServiceHandler extends BaseHanlder<CustomerService> {

	@Autowired
	private CustomerServiceService customerServiceService;
	@Autowired
	private DictService dictService;
	@Autowired
	private com.atguigu.crm.service.CustomerService customerService;
	@Autowired
	private UserService userService;
	
	/**
	 * 处理
	 */
	@RequestMapping(value="/deal",method=RequestMethod.POST)
	public String deal(){
		
		
		return "";
	}
	
	/**
	 * 跳转到处理页面
	 */
	@RequestMapping(value="/deal/{id}",method=RequestMethod.GET)
	public String toDealUI(@PathVariable("id") Long id, Map<String,Object> map){
		
		CustomerService service = customerServiceService.get(id);
		
		map.put("service", service);
		
		return "service/deal/deal";
	}
	
	
	/**
	 * 跳转到处理分页页面
	 */
	@RequestMapping("/allot/list")
	public String dealList(@RequestParam(value="pageNo",required=false) String pageNoStr,
			Map<String, Object> map,
			HttpServletRequest request){
		//map.put("EQS_state", "新创建");
		String key = "EQS_state";
		String value = "已分配";
		super.list(customerServiceService, pageNoStr, map, request,key,value);
		
		Set<User> users = userService.getAllUsers();
		map.put("users", users);
		
		return "service/deal/list";
	}
	
	
	/**
	 * 删除任务
	 */
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id){
		
		customerServiceService.delete(id);
		
		return "redirect:/service/allot/list";
	}
	
	/**
	 * 指派任务
	 */
	@RequestMapping("/allot")
	public String allot(@RequestParam("id") Long id, @RequestParam("allotId") Long allotId){
		
		User user = new User();
		user.setId(allotId);
		
		CustomerService cService = new CustomerService();
		cService.setId(id);
		cService.setAllotDate(new Date());
		cService.setAllotTo(user);
		cService.setServiceState("已分配");
		
		customerServiceService.allot(cService);
		
		return "1";
	}
	
	
	
	/**
	 * 跳转到分配页面
	 */
	@RequestMapping("/allot/list")
	public String allot(@RequestParam(value="pageNo",required=false) String pageNoStr,
			Map<String, Object> map,
			HttpServletRequest request){
		//map.put("EQS_state", "新创建");
		String key = "EQS_state";
		String value = "新创建";
		super.list(customerServiceService, pageNoStr, map, request,key,value);
		
		Set<User> users = userService.getAllUsers();
		map.put("users", users);
		
		return "service/allot/list";
	}
	
	
	/**
	 * 此方法用于新建
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String create(CustomerService customerService){
		
		customerServiceService.save(customerService);
		
		return "redirect:/service/allot/list";
	}
	
	
	
	/**
	 * 此方法用于跳转到新建页面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String toCreateUI(Map<String, Object> map){
		
		String flag = "服务类型";
		
		List<String> serviceTypes = dictService.get(flag);
		List<Customer> customers = customerService.getAllForList();
		
		map.put("serviceTypes", serviceTypes);
		map.put("customers", customers);
		
		return "service/input";
	}
	
}
