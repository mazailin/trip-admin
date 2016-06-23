package com.ulplanet.trip.modules.sys.listener;

import com.ulplanet.trip.common.utils.ServletContextHolder;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {

        ServletContextHolder.setServletContext(servletContext);

		return super.initWebApplicationContext(servletContext);
	}
}
