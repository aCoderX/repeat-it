package com.acoderx.beans.factory;

/**
 * Description:
 *
 * @author xudi
 * @since 2019-01-16
 */
public interface HierachicalBeanFactory extends BeanFactory {
    BeanFactory getParentBeanFactory();
}
