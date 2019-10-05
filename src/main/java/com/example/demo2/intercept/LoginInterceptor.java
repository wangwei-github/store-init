package com.example.demo2.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
/**
 * 登陆拦截器
 * @author soft01
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//验证用户是否登陆，如果登陆就放行，如果未登陆，拦截，重定向到登陆界面
		//获取session对象
		HttpSession session = request.getSession();
		if(session.getAttribute("uid")==null) {
			response.sendRedirect("/web/login.html");
			return false;
		}
		//放行
		return true;
	}
	
}
