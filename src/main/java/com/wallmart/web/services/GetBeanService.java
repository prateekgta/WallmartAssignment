package com.wallmart.web.services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GetBeanService {

	
	/**
	 * Method used to get bean object from beans.xml file.
	 * 
	 * @param name
	 *            of the class in string format defined in beans.xml.
	 * @return Object.
	 */
	public Object getBean(String beanName) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		Object beanObject = context.getBean(beanName);
		((ClassPathXmlApplicationContext) context).close();
		return beanObject;
	}

}
