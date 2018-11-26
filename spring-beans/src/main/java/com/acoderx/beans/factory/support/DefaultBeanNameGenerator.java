package com.acoderx.beans.factory.support;

import com.acoderx.beans.factory.config.BeanDefinition;

/**
 * Description:默认的BeanName生成器
 *
 * @author  xudi
 * @since  2018-11-25
 */
public class DefaultBeanNameGenerator implements BeanNameGenerator {

    /**
     * 默认的BeanName:类的首字母小写
     * @param beanDefinition
     * @return
     */
    @Override
    public String generateBeanName(BeanDefinition beanDefinition) {
        Class c = beanDefinition.getBeanClass();
        String s = c.getSimpleName();
        return String.valueOf(Character.toLowerCase(s.charAt(0))) + s.substring(1);
    }
}
