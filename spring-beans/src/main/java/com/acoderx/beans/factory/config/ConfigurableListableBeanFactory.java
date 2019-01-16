package com.acoderx.beans.factory.config;

import com.acoderx.beans.factory.HierachicalBeanFactory;

/**
 * Description:
 *
 * @author  xudi
 * @since  2018-11-22
 */
public interface ConfigurableListableBeanFactory extends HierachicalBeanFactory {
    void preInstantiateSingletons();

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
