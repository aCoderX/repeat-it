package com.acoderx.spring.web.method;

import java.lang.reflect.Method;

/**
 * Description:
 *
 * @author xudi
 * @since 2019-01-16
 */
public class HandlerMethod {
    private Method method;

    private Object bean;

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
