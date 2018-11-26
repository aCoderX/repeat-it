package com.acoderx.beans.factory.config;

import com.acoderx.beans.factory.BeanFactory;

/**
 * Description:beanFactory的后置处理器
 *
 * @author  xudi
 * @since  2018-11-13
 */
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(BeanFactory beanFactory);
}
