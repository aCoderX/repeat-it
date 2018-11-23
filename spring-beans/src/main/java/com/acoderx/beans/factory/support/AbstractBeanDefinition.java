package com.acoderx.beans.factory.support;

import com.acoderx.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;
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
    private Method factoryMethod;
    private String factoryMethodName;

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

    @Override
    public Method getFactoryMethod() {
        return factoryMethod;
    }

    @Override
    public String getFactoryMethodName() {
        return factoryMethodName;
    }

    public void setFactoryMethodName(String factoryMethodName) {
        this.factoryMethodName = factoryMethodName;
    }

    public void setFactoryMethod(Method factoryMethod) {
        this.factoryMethod = factoryMethod;
    }

    public void setPropertyValues(Map<String, Object> propertyValues) {
        this.propertyValues = propertyValues;
    }
}
