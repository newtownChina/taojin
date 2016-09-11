package com.taojin.interceptor;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 * 用处：所有请求jsp页面都跳装到首页，防止404
 * 类名：redirectJspRequestIntercepter
 * @author NXY
 * @version 1.0
 * @date 下午5:23:34
 */
public class redirectJspRequestIntercepter implements HandlerInterceptor {
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception)
			throws Exception {
		
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object object, ModelAndView exception) throws Exception {

	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object object) throws Exception {
		StringBuffer url = request.getRequestURL();  
		String u = url.toString();
		if(u.contains(".jsp")){
			response.sendRedirect("index.do");
		}
		return true;
	}
}
