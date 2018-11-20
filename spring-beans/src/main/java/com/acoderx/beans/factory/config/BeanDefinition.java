package com.acoderx.beans.factory.config;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-12
 */
public interface BeanDefinition {
    Class getBeanClass();

    boolean isSingleton();
}
