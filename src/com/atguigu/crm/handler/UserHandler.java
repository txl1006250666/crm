package com.atguigu.crm.handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Order;
import com.atguigu.crm.entity.Role;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.service.RoleService;
import com.atguigu.crm.service.UserService;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;

@RequestMapping("/user")
@Controller
public class UserHandler {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 此方法用于删除
	 */
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id,RedirectAttributes attributes){
		
		userService.delete(id);
		
		attributes.addFlashAttribute("message","删除成功！");
		return "redirect:/user/list"; 
	}
	
	/**
	 * 此方法用于修改
	 */
	@RequestMapping(value="/create",method=RequestMethod.PUT)
	public String update(User user,RedirectAttributes attributes){
		
		userService.update(user);
		attributes.addFlashAttribute("message","修改成功！");
		return "redirect:/user/list";
	}
	
	
	/**
	 * 此方法用于跳转修改页面
	 */
	@RequestMapping("/create/{id}")
	public String toEditUI(@PathVariable("id") Long id,Map<String, Object> map){
		
		User user = userService.get(id);
		List<Role> roles = roleService.getAllRoles();
		
		Map<Integer,String> allStatus = new HashMap<>();
		allStatus.put(1,"有效");
		allStatus.put(0,"无效");
		
		map.put("user", user);
		map.put("roles", roles);
		map.put("allStatus", allStatus);
		return "user/input";
	}
	
	/**
	 * 此方法用于新建
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String save(User user,RedirectAttributes attributes){
		
		userService.save(user);
		attributes.addFlashAttribute("message","添加成功！");
		return "redirect:/user/list";
	}
	
	
	/**
	 * 此方法用于跳转到新建页面
	 */
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String toInputUI(Map<String, Object> map){
		
		User user = new User();
		List<Role> roles = roleService.getAllRoles();
		
		Map<Integer,String> allStatus = new HashMap<>();
		allStatus.put(1,"有效");
		allStatus.put(0,"无效");
		
		map.put("user", user);
		map.put("roles", roles);
		map.put("allStatus", allStatus);
		return "user/input";
	}
	
	
	/**
	 * 此方法用于分页
	 * @param pageNoStr
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
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
		
		Page<User> page = userService.getPages(params,pageNo);
		
		map.put("page", page);
		map.put("queryString", queryString);
		return "user/list";
	}
	
	
	/**
	 * 此方法用于登陆
	 */
	@RequestMapping("/login")
	public String login(@RequestParam("name") String name,
			@RequestParam("password") String password,
			HttpSession session,
			RedirectAttributes redirectAttributes,
			Locale locale){
		
		User user = userService.login(name, password);
		
		/*if (name == "admin") {
			return "home/success";
		}*/
		
		if (user == null) {
			String code = "error.user.login";
			String message = messageSource.getMessage(code, null, locale);
			redirectAttributes.addFlashAttribute("message", message);
			return "redirect:/index";
		}
		
		session.setAttribute("user", user);
		
		return "home/success";
	}
	
}
