package com.atguigu.crm.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Authority;
import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.CustomerActivity;
import com.atguigu.crm.entity.Role;
import com.atguigu.crm.service.AuthorityService;
import com.atguigu.crm.service.RoleService;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;

@RequestMapping("/role")
@Controller
public class RoleHandler {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@RequestMapping(value="/assign", method=RequestMethod.PUT)
	public String assign(@ModelAttribute("role") Role role,
			@RequestParam("id") Long id,
			RedirectAttributes attributes){
		roleService.deleteAllAuthorities(id);
		
		if (role.getAuthorities2() != null && role.getAuthorities2().size() > 0) {
			roleService.batchSaveAuthorities(role); 
		}
		
		System.out.println(role.getAuthorities().size());
		attributes.addFlashAttribute("message", "设置角色成功!");
		
		return "redirect:/role/list";
	}
	/**
	 * 此方法用于分配权限
	 */
	@RequestMapping("assign/{id}")
	public String toAssign(@PathVariable("id") Long id,Map<String,Object> map){
		
		//List<Authority> parentAuthorities = authorityService.getParentAllAuth(id);
		
		Role role = roleService.get(id);
		List<Authority> parentAuthorities = authorityService.getParentAllAuth();
		
		List<Authority> authorities = role.getAuthorities();
		StringBuilder sb = new StringBuilder();
    	for(Authority authority :authorities){
    		sb.append(authority.getName()).append(",");
    	}
    	
    	map.put("userRoleStr", sb.toString());
		
		map.put("role", role);
		map.put("parentAuthorities", parentAuthorities);
		
		/*List<Authority> authorities = role.getAuthorities();
		
		for (Authority authority : authorities) {
			System.out.println(authority.getDisplayName());
		}*/
		
		return "role/assign";
	}
	
	
	/**
	 * 此方法用于删除角色
	 */
	@RequestMapping("delete/{id}")
	public String delete(@PathVariable("id") Long id){
		
		roleService.delete(id);
		
		return "redirect:/role/list";
	}
	
	/**
	 * 此方法用于新建角色
	 */
	@RequestMapping("/create")
	public String create(Role role){
		
		roleService.save(role);
		return "redirect:/role/list";
	}
	
	/**
	 * 此方法用于跳转新建页面
	 */
	@RequestMapping(value="/input",method=RequestMethod.GET)
	public String toInputUI(){
		
		
		return "role/input";
	}
	
	/**
	 * 此方法用于分页
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
		
		Page<Role> page = roleService.getPages(params,pageNo);
		
		map.put("page", page);
		
		return "role/list";
	}
	
}
