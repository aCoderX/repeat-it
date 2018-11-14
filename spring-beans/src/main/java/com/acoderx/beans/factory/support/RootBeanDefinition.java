package com.acoderx.beans.factory.support;

import com.acoderx.beans.factory.config.BeanDefinition;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-13
 */
public class RootBeanDefinition implements BeanDefinition {
    private Class aClass;

    public RootBeanDefinition(Class aClass) {
        this.aClass = aClass;
    }

    @Override
    public Class getBeanClass() {
        return aClass;
    }
}
