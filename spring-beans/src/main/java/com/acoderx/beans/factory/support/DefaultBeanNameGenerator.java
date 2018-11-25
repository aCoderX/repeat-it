package com.acoderx.beans.factory.support;

import com.acoderx.beans.factory.config.BeanDefinition;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-25
 */
public class DefaultBeanNameGenerator implements BeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition beanDefinition) {
        Class c = beanDefinition.getBeanClass();
        String s = c.getSimpleName();
        return String.valueOf(Character.toLowerCase(s.charAt(0))) + s.substring(1);
    }
}
