package com.acoderx.beans.factory.support;

import com.acoderx.beans.factory.config.BeanFactoryPostProcessor;

/**
 * Description:BeanFactory的后置处理器扩展，在所有BeanDefinition被注册后调用
 *
 * @author  xudi
 * @since  2018-11-13
 */
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {
    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanFactory);
}
