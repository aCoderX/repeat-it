package com.acoderx.beans.factory.config;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-20
 */
public interface SingletonBeanRegistry {
    Object getSingleton(String beanName);

    void registerSingleton(String beanName, Object singletonObject);
}
