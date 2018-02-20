package com.packt.tx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class TransactionTestManual {
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		FooService fooService = (FooService) context.getBean("fooService");
		System.out.println(fooService);
		
//		Totally Manual
		PlatformTransactionManager txManagerManual = (PlatformTransactionManager) context.getBean("txManager");
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		definition.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
		definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		
		TransactionStatus status = txManagerManual.getTransaction(definition);
		try {
			fooService.updateFoo(null);
			txManagerManual.commit(status);
		}
		catch(Exception e) {
			System.out.println("Inside rollback catch block");
			txManagerManual.rollback(status);
		}
		
//		With Template
		PlatformTransactionManager txManager = (PlatformTransactionManager) context.getBean("txManager");
		TransactionTemplate txTemplate = new TransactionTemplate(txManager);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.execute(new TransactionCallbackWithoutResult() {
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				System.out.println("Inside doInTransactionWithoutResult()");
				fooService.updateFoo(null);
			}
		});
		
	}
}
