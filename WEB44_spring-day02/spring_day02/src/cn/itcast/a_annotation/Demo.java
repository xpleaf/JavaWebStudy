package cn.itcast.a_annotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.User;

public class Demo {
	@Test
	public void fun1(){
		
		//1 创建容器对象
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		//2 向容器"要"user对象
		User u1 = (User) ac.getBean("user");
		User u2 = (User) ac.getBean("user");
		
		System.out.println(u1==u2);
		//3 打印user对象
		System.out.println(u1);
		
		ac.close();
		
		
	}
	
}
