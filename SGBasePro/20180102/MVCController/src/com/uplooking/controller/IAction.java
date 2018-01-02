package com.uplooking.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IAction {

	/**
	 * 1 默认转发 return "index.jsp"; 2 重定向 return "@red_index.jsp" 3json return
	 * "@json_{XXXXXX}"
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	String execute(HttpServletRequest req, HttpServletResponse resp);

}
