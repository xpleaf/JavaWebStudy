package com.itheima.mybatis.junit;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.itheima.mybatis.mapper.UserMapper;
import com.itheima.mybatis.pojo.User;

public class MybatisMapperTest {

	
	@Test
	public void testMapper() throws Exception {
		//加载核心配置文件
		String resource = "sqlMapConfig.xml";
		InputStream in = Resources.getResourceAsStream(resource);
		//创建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
		//创建SqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		//SqlSEssion帮我生成一个实现类  （给接口）
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		
		User user = userMapper.findUserById(10);
		System.out.println(user);
	}
}
