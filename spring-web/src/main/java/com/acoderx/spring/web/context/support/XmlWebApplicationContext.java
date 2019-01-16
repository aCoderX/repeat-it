package com.acoderx.spring.web.context.support;

import com.acoderx.beans.factory.support.DefaultListableBeanFactory;
import com.acoderx.beans.factory.xml.XmlBeanDefinitionReader;
import com.acoderx.spring.web.context.WebApplicationContext;
import com.acoderx.spring.context.support.AbstractRefreshableConfigApplicationContext;

import javax.servlet.ServletContext;

/**
 * Description:xml配置，默认配置文件是/WEB-INF/applicationContext.xml
 *
 * @author xudi
 * @since 2018-12-11
 */
public class XmlWebApplicationContext extends AbstractRefreshableConfigApplicationContext implements WebApplicationContext {
    public static final String DEFAULT_CONFIG_LOCATION = "/WEB-INF/applicationContext.xml";

    private ServletContext servletContext;

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        for (String configLocation : getConfigLocations()) {
            reader.loadBeanDefinition(configLocation);
        }
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    protected String[] getDefaultConfigLocations() {
        return new String[]{DEFAULT_CONFIG_LOCATION};
    }
}
