package com.onet.auth.spring.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.onet.auth.AuthNotRequired;
import com.onet.common.constant.CookieKey;
import com.onet.common.util.CookieUtil;
import com.onet.common.util.StringUtil;
import com.onet.user.constant.Role;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		AuthNotRequired annotation = method.getAnnotation(AuthNotRequired.class);
		//没注解
		String userId = CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName());
		if (null == annotation) {
			if (StringUtil.isEmpty(userId)) {
				response.sendRedirect("/user/signin");
				return false;
			}
			String uri = request.getRequestURI();
			if (uri.startsWith("/onet/admin")) {
				String role = CookieUtil.getCookie(request, CookieKey.USER_ROLE.getEnName());
				if (!Role.ADMIN.name().equals(role)) {
					response.sendRedirect("/common/denied");
					return false;
				}
			}
		} else if (null != userId) {
			response.sendRedirect("/note/list/all");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (null != modelAndView) {
			String userName = CookieUtil.getCookie(request, CookieKey.USER_NAME.getEnName());
			if (null != userName) {
				Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
				String role = CookieUtil.getCookie(request, CookieKey.USER_ROLE.getEnName());
				modelAndView.addObject("userid", userid);
				modelAndView.addObject("username", userName);
				modelAndView.addObject("role", role);
			}
		}
	}
}
