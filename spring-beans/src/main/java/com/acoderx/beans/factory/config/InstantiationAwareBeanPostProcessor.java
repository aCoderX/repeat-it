package com.acoderx.beans.factory.config;

/**
 * Description:
 *
 * @author  xudi
 * @since  2018-11-23
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * 在bean被实例化后，设置属性时被调用
     * @param o
     * @param beanDefinition
     */
    default void postProcessPropertyValues(Object o,BeanDefinition beanDefinition) {
    }
}
