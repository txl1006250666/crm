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
import com.atguigu.crm.service.ContactService;
import com.atguigu.crm.service.CustomerService;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;

@RequestMapping("/contact")
@Controller
public class ContactHandler {
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 此方法用于删除
	 */
	@RequestMapping("delete/{id}/{customerid}/{size}")
	public String delete(@PathVariable("id") Long id,
			@PathVariable("customerid") Long customerid,
			@PathVariable("size") Integer size,
			RedirectAttributes attributes){
		
		//Long id = Long.parseLong(idStr);
		//Long customerid = Long.parseLong(customeridStr);
		
		if (size <= 1) {
			attributes.addFlashAttribute("message","该联系人不能被删除！");
			return "redirect:/contact/list/" + customerid;
		}
		
		Contact contact = contactService.get(id);
		contact.setCustomer(new Customer());
		//先将本contact的customerId设为null
		contactService.update(contact);
		
		contactService.delete(id);
		
		return "redirect:/contact/list/" + customerid;
	}
	
	
	
	/**
	 * 此方法用于修改
	 */
	@RequestMapping(value="/create",method=RequestMethod.PUT)
	public String update(Contact contact){
		
		contactService.update(contact);
		return "redirect:/contact/list/" + contact.getCustomer().getId(); //id为customer.id
	}
	
	
	/**
	 * 此方法用于新建
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String save(Contact contact){
		
		contactService.save(contact);
		return "redirect:/contact/list/" + contact.getCustomer().getId(); //id为customer.id
	}
	
	/**
	 * 此方法用于跳转修改页面
	 */
	@RequestMapping("/create/{id}")
	public String toEditUI(@PathVariable("id") Long id,Map<String,Object> map){
		
		Contact contact = contactService.get(id);
		
		map.put("contact", contact);
		return "contact/input";
	}
	
	
	/**
	 * 此方法用于跳转到新建页面
	 */
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String toInputUI(@RequestParam("customerid") Long customerid,
			Map<String,Object> map){
		
		Contact contact = new Contact();
		
		map.put("customerid", customerid);
		map.put("contact", contact);
		return "contact/input";
	}
	
	
	/**
	 * 此方法用于跳转到list页面
	 */
	@RequestMapping("/list/{id}")
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
		
		Page<Customer> page = contactService.getPages(params,pageNo);
		
		//放入另两个参数
		Customer customer = customerService.get(id);
		int size = page.getContent().size();
		map.put("page", page);
		map.put("customer", customer);
		map.put("queryString", queryString);
		map.put("size", size);
		
		return "contact/list";
	}
}
