package com.packt.aop;

import org.springframework.aop.framework.ProxyFactory;

public class AOPTest {

	public static void main(String[] args) {
		//our target:
		MessageWriter target = new MessageWriter();
		
		//our aspect:
		ProxyFactory pf = new ProxyFactory();
		pf.addAdvice(new MessageDecorator());
		pf.setTarget(target);
		
		//in action
		MessageWriter proxy = (MessageWriter) pf.getProxy();
		proxy.writeMessage();
		
		//instead of
		target.writeMessage();

	}

}
