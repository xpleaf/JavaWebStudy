package com.itheima.mybatis.junit;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.itheima.mybatis.pojo.User;

public class MybatisFirstTest {

	//成员变量
	
	
	@Test
	public void testMybatis() throws Exception {
		//加载核心配置文件
		String resource = "sqlMapConfig.xml";
		InputStream in = Resources.getResourceAsStream(resource);
		//创建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
		//创建SqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		//执行Sql语句 
		User user = sqlSession.selectOne("test.findUserById", 10);
		
		System.out.println(user);
	}
	//根据用户名称模糊查询用户列表
	@Test
	public void testfindUserByUsername() throws Exception {
		//加载核心配置文件
		String resource = "sqlMapConfig.xml";
		InputStream in = Resources.getResourceAsStream(resource);
		//创建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
		//创建SqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		//执行Sql语句 
		List<User> users = sqlSession.selectList("test.findUserByUsername", "五");
		for (User user2 : users) {
			System.out.println(user2);
			
		}
	}
	//添加用户
	@Test
	public void testInsertUser() throws Exception {
		//加载核心配置文件
		String resource = "sqlMapConfig.xml";
		InputStream in = Resources.getResourceAsStream(resource);
		//创建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
		//创建SqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		//执行Sql语句 
		User user = new User();
		user.setUsername("何炅");
		user.setBirthday(new Date());
		user.setAddress("sadfsafsafs");
		user.setSex("男");
		int i = sqlSession.insert("test.insertUser", user);
		sqlSession.commit();
		
		System.out.println(user.getId());
		
	}
	//更新用户
	@Test
	public void testUpdateUserById() throws Exception {
		//加载核心配置文件
		String resource = "sqlMapConfig.xml";
		InputStream in = Resources.getResourceAsStream(resource);
		//创建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
		//创建SqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		//执行Sql语句 
		User user = new User();
		user.setId(29);
		user.setUsername("何炅292929");
		user.setBirthday(new Date());
		user.setAddress("222222sadfsafsafs");
		user.setSex("女");
		int i = sqlSession.update("test.updateUserById", user);
		sqlSession.commit();
	}
	//删除
	@Test
	public void testDelete() throws Exception {
		//加载核心配置文件
		String resource = "sqlMapConfig.xml";
		InputStream in = Resources.getResourceAsStream(resource);
		//创建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
		//创建SqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		sqlSession.delete("test.deleteUserById", 29);
		sqlSession.commit();
	}
}
