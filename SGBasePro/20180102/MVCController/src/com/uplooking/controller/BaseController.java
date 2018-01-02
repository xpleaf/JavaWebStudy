package com.uplooking.controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController extends HttpServlet {

	// 调用properties 配置文件
	private Properties properties = new Properties();
	// 字符编码集合
	private String charset = "utf-8";

	@Override
	public void init(ServletConfig config) throws ServletException {

		// 获取初始化的 参数 key value
		String configname = config.getInitParameter("configname");

		try {
			// 自动加载配置文件
			properties.load(this.getClass().getClassLoader().getResourceAsStream(configname));

			// 通过配置文件获取到字符编码集
			String tmp = properties.getProperty("charset");
			if (tmp != null) {
				charset = tmp;
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("读取失败！");
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 设置字符编码集
			req.setCharacterEncoding(charset);
			resp.setCharacterEncoding(charset);

			// 获取到 uri
			String act = req.getRequestURI();
			// System.out.println(act);
			// /MVCController/ user .do
			// user --- 找到Config文件的com.uplooking.action.UserAction
			act = act.substring(act.lastIndexOf("/") + 1, act.lastIndexOf(".do"));
			// 获取类路径 System.out.println(act);
			String classPath = properties.getProperty(act);
			// System.out.println(classPath);

			if (classPath == null || classPath.equals("")) {
				resp.setContentType("text/html");
				resp.getWriter().println("没有您要访问的路径！");
				return;
			}

			// 进行反射调用
			Class iActionClass = Class.forName(classPath);

			// 反向实例
			IAction iAction = (IAction) iActionClass.newInstance();

			// 交给 子方法进行处理 处理完之后 只要 返回 String request response
			// 封装返回值
			String result = iAction.execute(req, resp);
			System.out.println("返回值=" + result);

			// 有返回值的时候
			if (result != null && !"".equals(result)) {

				/*
				 * 1 默认转发 return "index.jsp"; 2 重定向 return "@red_index.jsp" 3json return
				 * "@json_{XXXXXX}"
				 */
				if (result.contains("@red_")) {
					// 重定向
					resp.sendRedirect(result.split("_")[1]);
				} else if (result.contains("@json_")) {
					resp.setContentType("text/html");
					resp.getWriter().println(result.split("_")[1]);
					resp.getWriter().close();
				} else {
					req.getRequestDispatcher(result).forward(req, resp);
				}

			} else {
				// 没有返回值的时候
				resp.setContentType("text/html");
				resp.getWriter().println("没有返回值！");
				return;
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
