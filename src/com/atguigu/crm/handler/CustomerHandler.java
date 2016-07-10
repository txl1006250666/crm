package com.atguigu.crm.handler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Contact;
import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.service.CustomerService;
import com.atguigu.crm.service.DictService;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;

@RequestMapping("/customer")
@Controller
public class CustomerHandler {
	
	@Autowired
	private DictService dictService;
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 此方法用于删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(@RequestParam("id") Long id){
		
		customerService.deleteByUpdate(id);
		
		return "1";
		
	}
	
	
	/**
	 * 此方法用于保存修改
	 */
	@RequestMapping("/create")
	public String Update(Customer customer,RedirectAttributes attributes){
		
		customerService.update(customer);
		
		attributes.addFlashAttribute("message","修改成功");
		return "redirect:/customer/list";
	}
	
	
	/**
	 * 此方法用于跳转编辑页面
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String toEditUI(@PathVariable("id") Long id,Map<String,Object> map){
		
		//Custom<Dict> dicts = dictService.getById();
		Customer customer = customerService.get(id);
		
		Set<Contact> contacts = customer.getContacts();
		for (Contact contact : contacts) {
			System.out.println(contact.getName());
		}
		
		//List<Contact> managers = customerService.getManagers(id);
		List<String> regions = dictService.getRegions();
		List<String> levels = dictService.getLevels();	
		List<String> satisfies = dictService.getSatisfies();
		List<String> credits = dictService.getCredits();
		
		map.put("customer", customer);
		map.put("regions", regions);
		map.put("levels", levels);
		map.put("satisfies", satisfies);
		map.put("credits", credits);
		//map.put("managers", managers);
		
		
		return "customer/input";
	}
	
	/**
	 * 此方法用于跳转到list页面
	 */
	@RequestMapping(value="/list")
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
		
		//获取page对象,客户名称，地区，客户经理，客户等级，状态
		//params.put("EQS_name", createBy);
		Page<Customer> page = customerService.getPages(params,pageNo);
		
		//放入另两个参数
		List<String> regions = dictService.getRegions();
		List<String> levels = dictService.getLevels();
		
		map.put("regions", regions);
		map.put("levels", levels);
		map.put("page", page);
		map.put("queryString", queryString);
		
		
		return "customer/list";
	}
	
}
