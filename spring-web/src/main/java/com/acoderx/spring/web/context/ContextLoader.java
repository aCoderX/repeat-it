package com.acoderx.spring.web.context;

import com.acoderx.spring.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContext;

/**
 * Description:
 *
 * @author xudi
 * @since 2018-12-11
 */
public class ContextLoader {
    private WebApplicationContext context;

    /**
     * 初始化root  applicationContext（默认获取WEB-INF/applicationContext.xml）
     * @param servletContext
     */
    public void initWebApplicationContext(ServletContext servletContext) {
        this.context = new XmlWebApplicationContext();
        ((XmlWebApplicationContext) context).setServletContext(servletContext);
        context.refresh();
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);
    }
}
