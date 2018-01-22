package com.packt.tx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TransactionTestAnnotation {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContextTxAnnotation.xml");
		FooService fooService = (FooService) context.getBean("fooService");
		System.out.println(fooService);
		fooService.updateFoo(null);
	}
}