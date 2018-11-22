package com.acoderx.beans.factory.config;

import com.acoderx.beans.factory.BeanFactory;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-22
 */
public interface ConfigurableListableBeanFactory extends BeanFactory {
    void preInstantiateSingletons();
}
