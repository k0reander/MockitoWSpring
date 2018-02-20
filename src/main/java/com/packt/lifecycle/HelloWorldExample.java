package com.packt.lifecycle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWorldExample {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		HelloWorld world = (HelloWorld) context.getBean("helloWorld");
		System.out.println(world.getMessage());		
	}

}
