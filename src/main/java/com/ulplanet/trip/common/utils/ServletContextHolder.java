package com.ulplanet.trip.common.utils;

import javax.servlet.ServletContext;

/**
 * 
 * ServletContext上下文环境的持有者.
 * @author zhangxd
 * @date 2015年7月12日
 *
 */
public class ServletContextHolder {
	
	private static ServletContext servletContext;
	
	public static void setServletContext(ServletContext servletContext) {
		ServletContextHolder.servletContext = servletContext;
	}
	
	public static ServletContext getServletContext() {
		checkServletContext();
		return servletContext;
	}

    public static String getAppPath() {
        return getServletContext().getRealPath("/");
    }
	
	/**
	 * 清除servletContext静态变量.
	 */
	public static void cleanServletContext() {
		servletContext = null;
	}
	
	public static boolean isContextReady() {
		return servletContext != null;
	}
	
	private static void checkServletContext() {
		if (servletContext == null) {
			throw new IllegalStateException("servletContext未设置");
		}
	}
}