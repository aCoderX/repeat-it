package com.acoderx.spring.web.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Description:在servlet启动时加载applicationContext（root）
 *
 * @author xudi
 * @since 2018-12-11
 */
public class ContextLoaderListener extends ContextLoader implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initWebApplicationContext(servletContextEvent.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
