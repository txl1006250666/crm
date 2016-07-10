package com.atguigu.crm.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestControllerAdvice2 {
	
	@RequestMapping("/testControllerAdvice2")
	public String test(){
		System.out.println(10 / 0);
		return "success";
	}
	
}
