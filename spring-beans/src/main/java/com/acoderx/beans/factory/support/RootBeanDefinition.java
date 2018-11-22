package com.acoderx.beans.factory.support;

import java.util.Map;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-13
 */
public class RootBeanDefinition extends AbstractBeanDefinition {

    public RootBeanDefinition(Class aClass) {
        super(aClass);
    }

    public RootBeanDefinition(Class aClass, Map<String, Object> propertyValues) {
        super(aClass, propertyValues);
    }
}
