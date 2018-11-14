package com.acoderx.beans.factory.config;

import com.acoderx.beans.factory.support.DefaultListableBeanFactory;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-13
 */
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(DefaultListableBeanFactory beanFactory);
}
