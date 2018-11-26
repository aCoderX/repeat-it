package com.acoderx.beans.factory;

/**
 * Description:
 *
 * @author  xudi
 * @since  2018-11-12
 */
public interface BeanFactory {
    <T> T getBean(Class<T> testBeanClass);

    Object getBean(String name);

    String[] getBeanNamesForType(Class type);
}
