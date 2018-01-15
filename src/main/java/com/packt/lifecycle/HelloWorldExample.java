package com.packt.lifecycle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWorldExample {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		context.registerShutdownHook();
		HelloWorld world = (HelloWorld) context.getBean("helloWorld");
		System.out.println(world.getMessage());
	}

}
