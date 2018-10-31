package com.acoderx.repeat.junit.simplest;

import com.acoderx.repeat.junit.Test;

import java.lang.reflect.Method;

/**
 * Description:最简单的实现
 *
 * @author: xudi
 * @since: 2018-10-26
 */
public class JUnitCore {

    /**
     * 运行测试类中的单个方法
     * @param clazz
     * @param method
     * @return
     */
    public Result run(Class clazz,String method) {
        Result result = new Result();
        try {
            Method m = clazz.getMethod(method);
            return run(clazz, m);
        } catch (Throwable throwable) {
            result.getFailure().add(throwable);
        }
        return result;
    }

    /**
     * 运行单个测试类
     * @param clazz
     * @return
     */
    public Result run(Class clazz) {
        Result result = new Result();
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            result.mergeResult(run(clazz, m));
        }
        return result;
    }

    /**
     * 运行多个测试类
     * @param clazz
     * @return
     */
    public Result run(Class... clazz) {
        Result result = new Result();
        for (Class c : clazz) {
            result.mergeResult(run(clazz));
        }
        return result;
    }

    private Result run(Class clazz,Method method){
        Result result = new Result();
        try {
            Test test = method.getAnnotation(Test.class);
            if (test != null) {
                method.invoke(clazz.newInstance());
            }
        } catch (Throwable throwable) {
            result.getFailure().add(throwable);
        }
        return result;
    }
}
