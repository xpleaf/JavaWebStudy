package cn.itheima.web.action;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itheima.domain.User;
import cn.itheima.service.CustomerService;
import cn.itheima.service.UserService;
import cn.itheima.service.impl.UserServiceImpl;

public class UserAction extends ActionSupport implements ModelDriven<User> {
	private User user = new User();
	
	
	
	public String login() throws Exception {
		//获得spring容器=>从Application域获得即可
		
		//1 获得servletContext对象
		ServletContext sc = ServletActionContext.getServletContext();
		//2.从Sc中获得ac容器
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
		//3.从容器中获得CustomerService
		UserService us = (UserService) ac.getBean("userService");
	//-----------------------------------------------------------------------------------
		//1 调用Service 执行登陆操作
		User u = us.login(user);
		//2 将返回的User对象放入session域作为登陆标识
		ActionContext.getContext().getSession().put("user", u);
		//3 重定向到项目的首页
		return "toHome";
	}










	@Override
	public User getModel() {
		return user;
	}

	
	
	
}
