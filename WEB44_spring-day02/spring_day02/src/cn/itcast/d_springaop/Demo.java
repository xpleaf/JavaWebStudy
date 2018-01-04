package cn.itcast.d_springaop;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.bean.User;
import cn.itcast.service.UserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:cn/itcast/d_springaop/applicationContext.xml")
public class Demo {
	@Resource(name="userService")
	private UserService us;
	
	@Test
	public void fun1(){
		us.save();
	}
	
}
