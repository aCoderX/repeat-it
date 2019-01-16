package com.acoderx.spring.web.context;

import com.acoderx.spring.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * Description:
 *
 * @author xudi
 * @since 2018-12-11
 */
public interface WebApplicationContext extends ApplicationContext {
    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";
    ServletContext getServletContext();
}
