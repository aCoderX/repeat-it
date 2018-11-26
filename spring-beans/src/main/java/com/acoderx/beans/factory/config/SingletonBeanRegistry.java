package com.acoderx.beans.factory.config;

/**
 * Description:单例的bean的注册接口
 *
 * @author  xudi
 * @since  2018-11-20
 */
public interface SingletonBeanRegistry {
    Object getSingleton(String beanName);

    void registerSingleton(String beanName, Object singletonObject);
}
