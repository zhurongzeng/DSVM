package com.dashuf.dsvm.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dashuf.dsvm.common.util.CommonUtil;
import com.dashuf.dsvm.usermanagement.dto.UserManagementDTO;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	/**
	 * 系统登陆页面
	 */
	private static final String LOGIN_PAGE = "login.html";
	private static final String LOGIN_REQUEST_VIEW = "loginUser.do";
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Object sessionObj = request.getSession().getAttribute(
				CommonUtil.SESSION_USER_INFO);

		// 获得请求路径的uri
		String uri = request.getRequestURI();
		// 进入登录页面，判断session中是否有key，有的话重定向到首页，否则进入登录界面
		if (uri.indexOf(LOGIN_REQUEST_VIEW) > 0) {
			if (sessionObj != null && sessionObj instanceof UserManagementDTO) {
				//response.sendRedirect(request.getContextPath()+"/"+LOGIN_REQUEST_VIEW);// 默认跟路径为首页
			} else {
				return true;
			}
		}

		// 其他情况判断session中是否有key，有的话继续用户的操作
		if (sessionObj != null && sessionObj instanceof UserManagementDTO) {
			return true;
		}

		// 最后的情况就是进入登录页面
		response.sendRedirect(LOGIN_PAGE);
		return false;

	}
	
}
