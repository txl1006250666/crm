package com.atguigu.crm.handler;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.service.SalesChanceService;
import com.atguigu.crm.service.UserService;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;

@RequestMapping("/chance")
@Controller
public class SalesChanceHandler {
	
	@Autowired
	private SalesChanceService salesChanceService;
	@Autowired
	private UserService userService;
	
	/**
	 * 此方法用于查看详情
	 */
	@RequestMapping("/detail/{chanceId}")
	public String detail(@PathVariable("chanceId") Long id,Map<String, Object> map){
		
		SalesChance chance = salesChanceService.getById(id);
		map.put("chance", chance);
		
		return "plan/detail";
	}
	
	/**
	 * 此方法用于开发成功
	 */
	@RequestMapping("/finish/{chanceId}")
	public String finish(@PathVariable("chanceId") Long chanceId, RedirectAttributes attributes){
		
		salesChanceService.finish(chanceId);
		
		attributes.addFlashAttribute("message","计划开发成功！");
		return "redirect:/plan/chance/list";
	}
	
	
	/**
	 * 此方法用于终止开发
	 */
	@RequestMapping("/stop/{id}")
	public String stop(@PathVariable("id") Integer id, RedirectAttributes attributes){
		
		attributes.addFlashAttribute("message","终止开发成功");
		salesChanceService.stop(id);
		
		return "redirect:/plan/chance/list";
	}
	
	/**
	 * 此方法用于分配工作
	 */
	@RequestMapping(value="/dispatch/{id}")
	public String dispatchWork(SalesChance chance,@PathVariable("id") Integer id){
		
		chance.setStatus(2);
		
		salesChanceService.dispatch(chance);	
		
		return "redirect:/chance/list";
	}
	
	
	/**
	 * 此方法用于跳转分配页面
	 */
	@RequestMapping(value="/dispatch/{id}",method=RequestMethod.GET)
	public String toDispatchUI(@PathVariable("id") Long id,Map<String,Object> map){
		
		SalesChance chance = salesChanceService.getById(id);
		
		Set<User> users = userService.getAllUsers();
		
		map.put("chance", chance);
		map.put("users", users);
		
		return "chance/dispatch";
	}
	
	/**
	 * 此方法用于修改
	 */
	@RequestMapping(value="/",method=RequestMethod.PUT)
	public String update(SalesChance chance,
			@RequestParam("pageNo") Integer pageNo,
			RedirectAttributes attributes){
		
		salesChanceService.update(chance);
		attributes.addFlashAttribute("message","修改成功！");
		
		return "redirect:/chance/list?pageNo="+pageNo;
	}
	
	
	/**
	 * 此方法用于跳转到修改页面（同增加页面）
	 */
	@RequestMapping(value="{id}",method=RequestMethod.GET)
	public String toEdit(@PathVariable("id") Long id,
			@RequestParam("pageNo") Integer pageNo,
			Map<String,Object> map){
		
		SalesChance chance = salesChanceService.getById(id);
		map.put("chance", chance);
		map.put("pageNo", pageNo);
		
		return "chance/input";
	}
	
	/**
	 * 此方法用于删除客户
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id,
			@RequestParam(value="pageNo",required=false) Integer pageNo,
			RedirectAttributes attributes){
		
		salesChanceService.delete(id);
		attributes.addFlashAttribute("message","删除成功！");
		
		return "redirect:/chance/list?pageNo="+pageNo;
	}
	
	
	/**
	 * 此方法用于添加客户
	 * @param salesChance
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.POST)
	public String save(SalesChance salesChance,
			RedirectAttributes attributes){
		
		salesChanceService.save(salesChance);
		attributes.addFlashAttribute("message", "添加成功！");
		return "redirect:/chance/list";
	}
	
	
	/**
	 * 此方法用于跳转到添加页面，由于是默认提交，故方法为get
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String toInputUI(Map<String, Object> map){
		
		map.put("chance", new SalesChance());
		return "chance/input";
	}
	
	/**
	 * 本方法用于跳转到list页面
	 * 1.获取参数集
	 * 2.传递参数集
	 * 3.解决乱码问题
	 */
	@RequestMapping("/list")
	public String getSalesChance(@RequestParam(value="pageNo",required=false) String pageNoStr,
			HttpSession session,
			Map<String, Object> map,
			HttpServletRequest request){
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataProcessUtils.transformMapToString(params);
		
		User createBy = (User) session.getAttribute("user");
		
		int pageNo = 1;
		
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		//获取参数
		//Map<String, String[]> newMap = DataProcessUtils.getObscureParamMap(request.getParameterMap(),"search_");
		
		//获取page对象
		params.put("EQO_createBy", createBy);
		params.put("EQI_status", 1);
		Page<SalesChance> page = salesChanceService.getPages(params,pageNo);
		
		map.put("page", page);
		map.put("queryString", queryString);
		
		return "chance/list";
	}


	/*private String transformMapToString(Map<String, Object> params) {
		
		StringBuilder sBuilder = new StringBuilder();
		
		//遍历map的entry的Set集合
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object val = entry.getValue();
			
			if (val == null || val.toString().equals("")) {
				continue;
			}
			
			sBuilder.append("&").append("search_").append(key).append("=").append(val);
		}
		
		return sBuilder.toString();
	}*/
	
}
