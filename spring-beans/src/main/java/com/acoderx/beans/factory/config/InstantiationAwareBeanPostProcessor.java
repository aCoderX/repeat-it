package com.acoderx.beans.factory.config;

/**
 * Description:
 *
 * @author  xudi
 * @since  2018-11-23
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    default void postProcessPropertyValues(Object o,BeanDefinition beanDefinition) {
    }
}
