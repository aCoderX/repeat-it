package com.acoderx.beans.factory;

/**
 * Description:
 *
 * @author  xudi
 * @since  2018-11-25
 */
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory);
}
