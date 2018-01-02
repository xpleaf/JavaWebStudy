package cn.itheima.b_api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.itheima.domain.Customer;

//学习Configuration对象
// Configuration功能: 配置加载类.用于加载主配置,orm元数据加载
public class Demo {

	@Test
	public void fun1(){
		//1 创建,调用空参构造
		Configuration conf = new Configuration();
		//2 读取指定主配置文件 => 空参加载方法,加载src下的hibernate.cfg.xml文件
		conf.configure();
		//3 读取指定orm元数据(扩展),如果主配置中已经引入映射配置.不需要手动加载
		//conf.addResource(resourceName);
		//conf.addClass(persistentClass);
		
		//4 根据配置信息,创建 SessionFactory对象
		SessionFactory sf = conf.buildSessionFactory();
		
	}
}
