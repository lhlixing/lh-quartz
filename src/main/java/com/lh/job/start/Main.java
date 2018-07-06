package com.lh.job.start;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动
 * @author lixin
 *
 */
public class Main {
	public static void main(String[] args) {
		ApplicationContext springContext = new ClassPathXmlApplicationContext(new String[] { "classpath:spring-core.xml"});
	}
}
