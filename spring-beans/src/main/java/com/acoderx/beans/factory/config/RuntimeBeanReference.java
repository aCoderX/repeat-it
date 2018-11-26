package com.acoderx.beans.factory.config;

/**
 * Description:运行时存储bean的引用
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
