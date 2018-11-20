package com.acoderx.beans.factory.support;

import com.acoderx.beans.factory.config.BeanDefinition;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-20
 */
public abstract class AbstractBeanDefinition implements BeanDefinition {
    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";

    private Class aClass;
    private String scope = SCOPE_SINGLETON;

    public AbstractBeanDefinition(Class aClass) {
        this.aClass = aClass;
    }

    public AbstractBeanDefinition(Class aClass, String scope) {
        this.aClass = aClass;
        this.scope = scope;
    }

    @Override
    public Class getBeanClass() {
        return aClass;
    }

    @Override
    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }
}
