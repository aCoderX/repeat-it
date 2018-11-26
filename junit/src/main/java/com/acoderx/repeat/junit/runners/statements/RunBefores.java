package com.acoderx.repeat.junit.runners.statements;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Description: statement的装饰者，会在被装饰者前运行方法
 *
 * @author  xudi
 * @since  2018-10-30
 */
public class RunBefores extends Statement {
    private List<Method> methodList;
    private Statement statement;
    private Object target;

    public RunBefores(List<Method> methodList, Object target, Statement statement) {
        this.methodList = methodList;
        this.statement = statement;
        this.target = target;
    }

    @Override
    public void evaluate() throws Throwable {
        for (Method method : methodList) {
            method.invoke(target);
        }
        statement.evaluate();
    }
}
