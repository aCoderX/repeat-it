package com.acoderx.beans.factory.support;

import com.acoderx.beans.factory.config.BeanDefinition;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-13
 */
public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String beanName);

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    String[] getBeanDefinitionNames();
}
