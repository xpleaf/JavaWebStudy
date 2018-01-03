package cn.itheima.web.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class LoginInterceptor extends MethodFilterInterceptor {
	//指定不拦截登陆方法. 其他方法都拦截
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		//1.获得session
		Map<String, Object> session = ActionContext.getContext().getSession();
		//2.获得登陆标识
		Object object = session.get("user");
		//3.判断登陆标识是否存在
		if(object == null){
			//不存在=>没登录=>重定向到登录页面
			return "toLogin";
		}else{
			//存在=>已经登陆=>放行
			return invocation.invoke();
		}
		
	}

}
