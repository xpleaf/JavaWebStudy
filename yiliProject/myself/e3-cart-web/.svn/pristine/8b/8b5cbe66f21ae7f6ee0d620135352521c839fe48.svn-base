package cn.e3mall.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

/**
 * 判断用户是否登录的拦截器
 * 
 * @version 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 执行handler方法之前执行此方法
		// 1、实现一个HandlerInterceptor接口。
		// 2、在执行handler方法之前做业务处理
		// 3、从cookie中取token。使用CookieUtils工具类实现。
		String token = CookieUtils.getCookieValue(request, "token");
		// 4、没有取到token，用户未登录。放行
		if (StringUtils.isBlank(token)) {
			return true;
		}
		// 5、取到token，调用sso系统的服务，根据token查询用户信息。
		E3Result e3Result = tokenService.getUserByToken(token);
		// 6、没有返回用户信息。登录已经过期，未登录，放行。
		if (e3Result.getStatus() != 200) {
			return true;
		}
		// 7、返回用户信息。用户是登录状态。可以把用户对象保存到request中，在Controller中可以通过判断request中是否包含用户对象，确定是否为登录状态。
		TbUser user = (TbUser) e3Result.getData();
		request.setAttribute("user", user);
		// 返回true放行
		// 返回false拦截
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 执行handler方法之后，并且是返回ModelAndView对象之前

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 返回ModelAndView之后。可以捕获异常。

	}

}
