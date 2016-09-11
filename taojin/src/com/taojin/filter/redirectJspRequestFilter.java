package com.taojin.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class redirectJspRequestFilter implements Filter {
	@Override
	public void destroy() {

	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		StringBuffer sb = httpRequest.getRequestURL();
		String url = sb.toString();
		if(url.contains(".jsp")){
			httpResponse.sendRedirect("index.do");
		}
		filterChain.doFilter(httpRequest, httpResponse);
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}
