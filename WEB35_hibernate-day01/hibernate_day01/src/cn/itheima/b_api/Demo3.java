package cn.itheima.b_api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.itheima.domain.Customer;

//学习Session对象
//session对象功能: 表达hibernate框架与数据库之间的连接(会话).session类似于
//				JDBC年代的connection对象. 还可以完成对数据库中数据的增删改查操作.
//				session是hibernate操作数据库的核心对象
public class Demo3 {

	@Test
	//事务操作
	public void fun1(){
		//1 创建,调用空参构造
		Configuration conf = new Configuration().configure();
		//2 根据配置信息,创建 SessionFactory对象
		SessionFactory sf = conf.buildSessionFactory();
		//3 获得session
		Session session = sf.openSession();
		//4 session获得操作事务的Transaction对象
		//获得操作事务的tx对象
		//Transaction tx = session.getTransaction();
		//开启事务并获得操作事务的tx对象(建议使用)
		Transaction tx2 = session.beginTransaction();
		//----------------------------------------------
		
		
		//----------------------------------------------
		tx2.commit();//提交事务
		tx2.rollback();//回滚事务
		session.close();//释放资源
		sf.close();//释放资源
	}
	
	@Test
	//session的新增
	public void fun2(){
		//1 创建,调用空参构造
		Configuration conf = new Configuration().configure();
		//2 根据配置信息,创建 SessionFactory对象
		SessionFactory sf = conf.buildSessionFactory();
		//3 获得session
		Session session = sf.openSession();
		//4 session获得操作事务的Transaction对象
		//获得操作事务的tx对象
		//Transaction tx = session.getTransaction();
		//开启事务并获得操作事务的tx对象(建议使用)
		Transaction tx2 = session.beginTransaction();
		//----------------------------------------------
		Customer c = new Customer();
		c.setCust_name("传智播客");
		
		session.save(c);
		//----------------------------------------------
		tx2.commit();//提交事务
		session.close();//释放资源
		sf.close();//释放资源
	}
	@Test
	//session的查询
	//查询id为1的customer对象
	public void fun3(){
		//1 创建,调用空参构造
		Configuration conf = new Configuration().configure();
		//2 根据配置信息,创建 SessionFactory对象
		SessionFactory sf = conf.buildSessionFactory();
		//3 获得session
		Session session = sf.openSession();
		//4 session获得操作事务的Transaction对象
		//获得操作事务的tx对象
		//Transaction tx = session.getTransaction();
		//开启事务并获得操作事务的tx对象(建议使用)
		Transaction tx2 = session.beginTransaction();
		//----------------------------------------------
		
		Customer customer = session.get(Customer.class, 1l);
		
		System.out.println(customer);
		//----------------------------------------------
		tx2.commit();//提交事务
		session.close();//释放资源
		sf.close();//释放资源
	}
	@Test
	//session的修改
	//修改id为1的customer对象的name属性为黑马程序员
	public void fun4(){
		//1 创建,调用空参构造
		Configuration conf = new Configuration().configure();
		//2 根据配置信息,创建 SessionFactory对象
		SessionFactory sf = conf.buildSessionFactory();
		//3 获得session
		Session session = sf.openSession();
		//4 session获得操作事务的Transaction对象
		//获得操作事务的tx对象
		//Transaction tx = session.getTransaction();
		//开启事务并获得操作事务的tx对象(建议使用)
		Transaction tx2 = session.beginTransaction();
		//----------------------------------------------
		//1 获得要修改的对象
		Customer c = session.get(Customer.class, 1l);
		//2 修改
		c.setCust_name("黑马程序员");
		//3 执行update
		session.update(c);
		//----------------------------------------------
		tx2.commit();//提交事务
		session.close();//释放资源
		sf.close();//释放资源
	}
	@Test
	//session的删除
	//删除id为1的customer对象
	public void fun5(){
		//1 创建,调用空参构造
		Configuration conf = new Configuration().configure();
		//2 根据配置信息,创建 SessionFactory对象
		SessionFactory sf = conf.buildSessionFactory();
		//3 获得session
		Session session = sf.openSession();
		//4 session获得操作事务的Transaction对象
		//获得操作事务的tx对象
		Transaction tx = session.getTransaction();
		tx.begin();
		//开启事务并获得操作事务的tx对象(建议使用)
		Transaction tx2 = session.beginTransaction();
		//----------------------------------------------
		//1 获得要修改的对象
		Customer c = session.get(Customer.class, 1l);
		//2 调用delete删除对象
		session.delete(c);
		//----------------------------------------------
		tx2.commit();//提交事务
		session.close();//释放资源
		sf.close();//释放资源
	}
}
