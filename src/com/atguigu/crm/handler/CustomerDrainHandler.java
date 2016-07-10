package com.atguigu.crm.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.CustomerDrain;
import com.atguigu.crm.service.CustomerDrainService;
import com.atguigu.crm.service.CustomerService;
import com.atguigu.crm.utils.DataProcessUtils;
import com.atguigu.crm.utils.Page;

@RequestMapping("/drain")
@Controller
public class CustomerDrainHandler extends BaseHanlder{
	
	@Autowired
	private CustomerDrainService customerDrainService;
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 此方法用于确认流失
	 */
	@RequestMapping(value="/confirm")
	public String confirm(@RequestParam("id") Long id,@RequestParam("reason") String reason,
			RedirectAttributes attributes){
		
		CustomerDrain drain = customerDrainService.get(id);
		
		//在customer_drain表中更新reason、date、status
		drain.setReason(reason);
		drain.setDrainDate(new Date());
		drain.setStatus("流失");
		
		customerDrainService.update(drain);
		
		customerService.updateStatusByDrain(drain.getCustomer().getId());
		
		attributes.addFlashAttribute("message","操作成功！");
		
		return "redirect:/drain/list";
	}
	
	
	/**
	 * 此方法用于跳转确认流失
	 */
	@RequestMapping(value="/toConfirm/{id}")
	public String toConfirmUI(@PathVariable("id") Long id,Map<String,Object> map){
		
		CustomerDrain drain = customerDrainService.get(id);
		String delay = drain.getDelay();
		
		List<String> delays = null;
		if (delay != null) {
			String[] result = delay.split("`");
			delays = Arrays.asList(result);
		}
		
		map.put("drain", drain);
		map.put("delays", delays);
		return "drain/confirm";
	}
	
	
	
	/**
	 * 此方法使用ajax新建暂缓流失
	 */
	@RequestMapping("/delay")
	@ResponseBody
	public String save(@RequestParam("delay") String delay,
			@RequestParam("id") Long id){
		
		CustomerDrain drain = customerDrainService.get(id);
		
		if(drain.getDelay() == null){
			drain.setDelay(delay);			
		}else{
			drain.setDelay(drain.getDelay() + "`" + delay);						
		}
		
		customerDrainService.update(drain);
		
		return "1";
	}
	
	
	/**
	 * 跳转到暂缓流失页面
	 */
	@RequestMapping("/delay/{id}")
	public String delay(@PathVariable("id") Long id,Map<String,Object> map){
		
		CustomerDrain drain = customerDrainService.get(id);
		
		String delay = drain.getDelay();
		String[] result = delay.split("`");
		List<String> delays = Arrays.asList(result);
		
		map.put("drain", drain);
		map.put("delays", delays);
		
		return "drain/delay";
	}
	
	
	
	/**
	 * 此方法用于分页
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="pageNo",required=false) String pageNoStr,
			HttpSession session,
			Map<String, Object> map,
			HttpServletRequest request){
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataProcessUtils.transformMapToString(params);
		
		int pageNo = 1;
		
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		
		//获取page对象
		params.put("EQS_status", "流失预警");
		Page<CustomerDrain> page = customerDrainService.getPages(params,pageNo);
		
		map.put("page", page);
		map.put("queryString", queryString);
		
		return "drain/list";
	}


}
