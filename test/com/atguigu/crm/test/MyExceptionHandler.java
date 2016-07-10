package com.atguigu.crm.test;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

	@ExceptionHandler({ArithmeticException.class})
	public String handleException(){
		System.out.println("≥ˆ“Ï≥£¡À");
		return "error";
	}
	
}
