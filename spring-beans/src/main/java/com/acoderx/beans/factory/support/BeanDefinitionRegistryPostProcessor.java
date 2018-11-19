package com.acoderx.beans.factory.support;

import com.acoderx.beans.factory.config.BeanFactoryPostProcessor;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-13
 */
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {
    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanFactory);
}
