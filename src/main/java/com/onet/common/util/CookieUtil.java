package com.onet.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static final int ONE_DAY = 60 * 60 * 24;
	private static final String domain = "www.onet.com";
	
	/**
     * 
     * @param maxAge 单位为秒
     */
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
	    Cookie cookie = new Cookie(name,value);
	    cookie.setPath("/");
	    cookie.setDomain(domain);
	    if(maxAge>0)  cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	}
	
	/**
	 * 添加一个无生命周期的cookie
	 */
	public  static void addCookie(HttpServletResponse response,String name,String value){
		addCookie(response,name,value,0);
	}
	
	/**
	 * 获取一个cookie
	 */
	public static String getCookie(HttpServletRequest request , String name){
		if(request==null) return null;
		Cookie[] cookies = request.getCookies();
		if(cookies==null){return null;}
		for(Cookie cookie : cookies){
			if(cookie.getName().equals(name)){
				return cookie.getValue();
			}
		}
		return null;
	}
	
	/**
	 * 删除cookie
	 */
	public static void removeCookie(HttpServletRequest req,HttpServletResponse res,String name){
		String cookieValue = getCookie(req,name);
        if(null != cookieValue) {
            Cookie cookie = new Cookie(name,null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            cookie.setDomain(domain);
            res.addCookie(cookie);
        }
	}
	
	/**
	 * 删除所有cookie
	 */
	public static void removeAllCookie(HttpServletRequest req,HttpServletResponse res){
		Cookie[] cookies = req.getCookies();
		if(cookies==null) return;
		for(Cookie cookie : cookies){
			String name = cookie.getName();
			Cookie cookie2 = new Cookie(name,null);
            cookie2.setMaxAge(0);
            cookie2.setPath("/");
            cookie2.setDomain(domain);
            res.addCookie(cookie2);
		}
	}
}
