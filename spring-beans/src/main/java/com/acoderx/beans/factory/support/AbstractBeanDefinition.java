package com.acoderx.beans.factory.support;

import com.acoderx.beans.factory.config.BeanDefinition;

import java.util.Map;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-20
 */
public abstract class AbstractBeanDefinition implements BeanDefinition {

    private Class aClass;
    private String scope = SCOPE_SINGLETON;
    private Map<String, Object> propertyValues;

    public AbstractBeanDefinition(Class aClass) {
        this.aClass = aClass;
    }

    public AbstractBeanDefinition(Class aClass, Map<String, Object> propertyValues) {
        this.aClass = aClass;
        this.propertyValues = propertyValues;
    }

    @Override
    public Class getBeanClass() {
        return aClass;
    }

    @Override
    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public Map<String, Object> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(Map<String, Object> propertyValues) {
        this.propertyValues = propertyValues;
    }
}
