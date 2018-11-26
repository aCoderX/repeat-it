package com.acoderx.repeat.junit.runners.statements;

import java.lang.reflect.Method;

/**
 * Description: statement的实现类
 *
 * @author  xudi
 * @since  2018-10-30
 */
public class InvokeMethod extends Statement {
    private Method method;
    private Object target;

    public InvokeMethod(Method method, Object target) {
        this.method = method;
        this.target = target;
    }

    @Override
    public void evaluate() throws Throwable {
        method.invoke(target);
    }
}
