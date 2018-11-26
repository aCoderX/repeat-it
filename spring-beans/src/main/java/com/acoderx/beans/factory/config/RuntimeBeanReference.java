package com.acoderx.beans.factory.config;

/**
 * Description:
 *
 * @author  xudi
 * @since  2018-11-22
 */
public class RuntimeBeanReference {
    private String beanName;

    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
