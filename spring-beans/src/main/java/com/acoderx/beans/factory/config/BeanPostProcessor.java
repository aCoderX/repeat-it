package com.acoderx.beans.factory.config;

/**
 * Description:bean的后置处理器
 *
 * @author  xudi
 * @since  2018-11-23
 */
public interface BeanPostProcessor {
    default Object postProcessrBeforeInitiallization(Object bean){
        return bean;
    }

    default Object postProcessrAfterInitiallization(Object bean){
        return bean;
    }
}
