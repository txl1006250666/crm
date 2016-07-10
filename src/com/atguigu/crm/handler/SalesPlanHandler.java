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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.SalesPlan;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.service.SalesChanceService;
import com.atguigu.crm.service.SalesPlanService;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;

@RequestMapping("/plan")
@Controller
public class SalesPlanHandler {

	@Autowired
	private SalesChanceService salesChanceService;

	@Autowired
	private SalesPlanService salesPlanService;
	
	
	/**
	 * 此方法用于保存结果
	 */
	@RequestMapping("/execute")
	@ResponseBody
	public String excuteSaveAjax(@RequestParam("id") Long id,@RequestParam("result") String result){
		
		salesPlanService.saveResult(id,result);
		
		return "1";
	}
	
	
	/**
	 * 此方法用于跳转执行页面
	 */
	@RequestMapping("/execution/{id}")
	public String execute(@PathVariable("id") Long id,Map<String,Object> map){
		
		SalesChance chance = salesChanceService.getById(id);
		map.put("chance", chance);
		
		return "plan/exec";
	}
	
	/**
	 * 此方法用于ajax删除计划
	 */
	@RequestMapping("/delete-ajax")
	@ResponseBody
	public String deleteAjax(@RequestParam("id") Long id){
		
		salesPlanService.deleteById(id);
		
		return "1";
	}
	
	/**
	 * 此方法用于ajax修改计划
	 */
	@RequestMapping("/make-ajax")
	@ResponseBody
	public String updateAjax(@RequestParam("id") Long id,@RequestParam("todo") String todo){
		
		SalesPlan plan = new SalesPlan();
		plan.setId(id);
		plan.setTodo(todo);
		
		salesPlanService.update(plan);
		
		return "1";
		
	}
	
	
	/**
	 * 此方法用于新建计划
	 */
	@RequestMapping(value="/make/{chanceId}",method=RequestMethod.POST)
	public String make(SalesPlan plan, @PathVariable("chanceId") Long id,
			Map<String, Object> map){
		
		salesPlanService.save(plan);
		
		/*//
		SalesChance chance = (SalesChance) session.getAttribute("chance");
		//如果chance没有salesPlans，就创建一个
		if (chance.getSalesPlans() == null) {
			chance.setSalesPlans(new HashSet<SalesPlan>());
		}
			
		Set<SalesPlan> plans = chance.getSalesPlans();
		plans.add(plan);
		chance.setSalesPlans(plans);
		
		session.setAttribute("chance", chance);*/
		
		SalesChance chance = salesChanceService.getById(id);
		map.put("chance", chance);
		
		return "redirect:/plan/make/"+id;
	}
	
	
	/**
	 * 此方法用于跳转到制定计划页面
	 */
	@RequestMapping(value="/make/{chanceId}",method=RequestMethod.GET)
	public String toMakeUI(@PathVariable("chanceId") Long id,Map<String, Object> map){
		
		SalesChance chance = salesChanceService.getById(id);
		
		Set<SalesPlan> salesPlans = chance.getSalesPlans();
		for (SalesPlan salesPlan : salesPlans) {
			System.out.println(salesPlan.getTodo());
		}
		
		map.put("chance", chance);
		
		return "plan/make";
	}
	
	
	/**
	 * 此方法用于跳转到客户开发计划列表页面
	 */
	@RequestMapping("chance/list")
	public String getSalesChance(@RequestParam(value="pageNo",required=false) String pageNoStr,
			HttpSession session,
			Map<String, Object> map,
			HttpServletRequest request){
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataProcessUtils.transformMapToString(params);
		
		System.out.println(queryString);
		
		User designee = (User) session.getAttribute("user");
		
		int pageNo = 1;
		
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		//获取page对象
		params.put("EQO_designee", designee);
		params.put("EQI_status", 1);
		Page<SalesChance> page = salesChanceService.getPages(params,pageNo);
		
		map.put("page", page);
		map.put("queryString", queryString);
		
		return "plan/list";
	}

}
