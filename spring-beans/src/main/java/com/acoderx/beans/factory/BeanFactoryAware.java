package com.acoderx.beans.factory;

/**
 * Description:实现了该接口的类，初始化时会被注入beanFactory
 *
 * @author  xudi
 * @since  2018-11-25
 */
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory);
}
