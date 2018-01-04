package cn.itcast.c_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cn.itcast.service.UserService;
import cn.itcast.service.UserServiceImpl;
//观光代码=>动态代理
public class UserServiceProxyFactory implements InvocationHandler {
	
	public UserServiceProxyFactory(UserService us) {
		super();
		this.us = us;
	}

	private UserService us;
	
	public UserService getUserServiceProxy(){
		//生成动态代理
		UserService usProxy = (UserService) Proxy.newProxyInstance(UserServiceProxyFactory.class.getClassLoader(),
									UserServiceImpl.class.getInterfaces(), 
									this);
		//返回
		return usProxy;
		
	}

	@Override
	public Object invoke(Object arg0, Method method, Object[] arg2) throws Throwable {
		System.out.println("打开事务!");
		Object invoke = method.invoke(us, arg2);
		System.out.println("提交事务!");
		return invoke;
	}

}
