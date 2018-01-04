package cn.itcast.c_proxy;

import org.junit.Test;

import cn.itcast.service.UserService;
import cn.itcast.service.UserServiceImpl;

public class Demo {
	
	@Test
	//动态代理
	public void fun1(){
		UserService us = new UserServiceImpl();
		
		UserServiceProxyFactory factory = new UserServiceProxyFactory(us);
		
		UserService usProxy = factory.getUserServiceProxy();
		
		usProxy.save();
		
		//代理对象与被代理对象实现了相同的接口
		//代理对象 与 被代理对象没有继承关系
		System.out.println(usProxy instanceof UserServiceImpl );//false
	}
	
	@Test
	public void fun2(){
		
		UserServiceProxyFactory2 factory = new UserServiceProxyFactory2();
		
		UserService usProxy = factory.getUserServiceProxy();
		
		usProxy.save();
		
		//判断代理对象是否属于被代理对象类型
		//代理对象继承了被代理对象=>true
		System.out.println(usProxy instanceof UserServiceImpl );//true
	}
}
