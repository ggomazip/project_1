package com.project.www.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter{
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest hRequest = (HttpServletRequest)request;
		hRequest.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession hSession = hRequest.getSession();
		HttpServletResponse h_response = (HttpServletResponse)response;
		String id = (String)hSession.getAttribute("id");
		String path = hRequest.getRequestURI();
		boolean flag =false;
		flag = path.contains("member_login") || path.contains("member_register")
				|| path.contains("member_idcheck") || path.contains("member_emailcheck")
				|| path.contains("jquery-3.6.0.min.js");
		
		if(!flag &&( id == null || id.trim().length() == 0)) {	
				h_response.sendRedirect("member_login.jsp");			
		}else {
			chain.doFilter(request, response);
		}
	}
}
