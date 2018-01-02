package com.uplooking.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uplooking.controller.IAction;

public class UserAction implements IAction {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("UserAction");
		return "index.jsp";
	}
	
	
/*
 *   BaseController.java  把  HttpServletRequest req, HttpServletResponse resp
 *   通过反射 交给指定的类的 方法去处理 
 *   
 *             |
 *   		   |
 *   
 *          处理逻辑方法
 *   
 *   处理完之后        转发
 *            重定向
 *            json
 *            
 *      String   X指向   _   访问的页面/数据    
 *             |
 *             |
 *             |
 *      这个String --BaseController   
 *   
 * 	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		return null;
	}
	
	*/

}
