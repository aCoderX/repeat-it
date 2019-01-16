package com.acoderx.beans.factory;

/**
 * Description:
 *
 * @author xudi
 * @since 2019-01-16
 */
public interface InitializingBean {
    /**
     * 在bean初始化时，属性设置完成之后被调用
     */
    void afterPropertiesSet();
}
