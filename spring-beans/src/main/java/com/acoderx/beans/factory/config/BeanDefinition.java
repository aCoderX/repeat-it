package com.acoderx.beans.factory.config;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Description:
 *
 * @author  xudi
 * @since  2018-11-12
 */
public interface BeanDefinition {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    Class getBeanClass();

    boolean isSingleton();

    Map<String, Object> getPropertyValues();

    String getFactoryMethodName();

    Method getFactoryMethod();

}
