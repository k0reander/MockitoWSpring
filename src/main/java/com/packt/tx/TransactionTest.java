package com.packt.tx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TransactionTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		FooService fooService = (FooService) context.getBean("fooService");
		System.out.println(fooService);
		fooService.getFoo(null);
	}
}
