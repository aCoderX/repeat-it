package com.aoderx.spring.context.support;

import com.acoderx.beans.factory.support.DefaultListableBeanFactory;
import com.acoderx.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * Description:解析xml的ApplicationContext
 *
 * @author  xudi
 * @since  2018-11-16
 */
public class ClassPathXmlApplicationContext extends AbstractRefreshableConfigApplicationContext {

    public ClassPathXmlApplicationContext(String... configLocations) {
        setConfigLocations(configLocations);
        refresh();
    }

    /**
     * 解析configLocations，解析为beanDefinition并注册
     * @param beanFactory
     */
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        for (String configLocation : getConfigLocations()) {
            reader.loadBeanDefinition(configLocation);
        }
    }

}
