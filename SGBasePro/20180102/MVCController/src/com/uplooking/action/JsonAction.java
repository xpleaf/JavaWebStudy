package com.uplooking.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uplooking.controller.IAction;

public class JsonAction implements IAction {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {

		System.out.println("JsonAction");
		return "@json_{asdsadsadsadsadad}";
	}

}
