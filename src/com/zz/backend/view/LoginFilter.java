package com.zz.backend.view;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class LoginFilter implements Filter {

	private String filterPattern;
	private Logger logger = Logger.getLogger(LoginFilter.class);
	
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		//requestURI = ?
		String requestURI = request.getRequestURI();
		logger.debug(requestURI);
		
		String page = requestURI.substring(request.getContextPath().length());
		
		//�ж�HTTP SESSION���Ƿ���LOGIN_ADMIN
		String loginAdmin = (String)request.getSession().getAttribute("LOGIN_ADMIN");

		if(page.matches(filterPattern)){
			
			if(loginAdmin == null && !page.equals("/backend/login.jsp") && !page.equals("/backend/LoginServlet")
					){
				//redirect��login.jsp
				response.sendRedirect(request.getContextPath()+"/backend/login.jsp");
				logger.debug("����ת���¼ҳ�棡");
				return;
			}
		}
		//��������ִ��
		chain.doFilter(req, resp);
		
	}

	public void init(FilterConfig config) throws ServletException {
		filterPattern = config.getInitParameter("filterPattern");
		logger.debug("LoginFilter����ʼ���ˣ�");
	}

}
