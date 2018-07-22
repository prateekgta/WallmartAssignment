package com.wallmart.web.services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GetBeanService {

	public Object getBean(String beanName) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		Object beanObject = context.getBean(beanName);
		((ClassPathXmlApplicationContext) context).close();
		return beanObject;
	}

}
