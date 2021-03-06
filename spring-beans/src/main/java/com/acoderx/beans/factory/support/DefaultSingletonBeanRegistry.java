package com.acoderx.beans.factory.support;

import com.acoderx.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:单例bean注册的默认实现
 *
 * @author  xudi
 * @since  2018-11-20
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    //存放单例bean
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
