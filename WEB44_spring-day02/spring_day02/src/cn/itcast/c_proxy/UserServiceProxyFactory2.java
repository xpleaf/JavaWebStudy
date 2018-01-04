package cn.itcast.c_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import cn.itcast.service.UserService;
import cn.itcast.service.UserServiceImpl;

//观光代码=>cglib代理
public class UserServiceProxyFactory2 implements MethodInterceptor {
	

	public UserService getUserServiceProxy(){
		
		Enhancer en = new Enhancer();//帮我们生成代理对象
		
		en.setSuperclass(UserServiceImpl.class);//设置对谁进行代理
		
		en.setCallback(this);//代理要做什么
		
		UserService us = (UserService) en.create();//创建代理对象
		
		return us;
	}

	@Override
	public Object intercept(Object prxoyobj, Method method, Object[] arg, MethodProxy methodProxy) throws Throwable {
		//打开事务
		System.out.println("打开事务!");
		//调用原有方法
		Object returnValue = methodProxy.invokeSuper(prxoyobj, arg);
		//提交事务
		System.out.println("提交事务!");
		
		return returnValue;
	}


}
