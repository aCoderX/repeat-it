package com.acoderx.beans.factory.support;

import com.acoderx.beans.factory.config.BeanDefinition;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-25
 */
public interface BeanNameGenerator {
    String generateBeanName(BeanDefinition beanDefinition);
}
