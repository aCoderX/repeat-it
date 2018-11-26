package com.acoderx.repeat.junit.runner;

import com.acoderx.repeat.junit.runner.manipulation.Filter;
import com.acoderx.repeat.junit.runner.manipulation.Filters;
import com.acoderx.repeat.junit.runner.requests.ClassRequest;
import com.acoderx.repeat.junit.runner.requests.ClassesRequest;
import com.acoderx.repeat.junit.runner.requests.FilterRequest;

/**
 * Description:一次测试请求,直接决定了生成什么runner
 *
 * @author  xudi
 * @since  2018-10-29
 */
public abstract class Request {
    public abstract Runner getRunner();

    /**
     * 一个类的测试
     * @param c
     * @return
     */
    public static Request aClass(Class c) {
        return new ClassRequest(c);
    }

    /**
     * 多个类的测试
     * @param classes
     * @return
     */
    public static Request classes(Class... classes) {
        return new ClassesRequest(classes);
    }

    /**
     * 一个方法的测试
     * @param c
     * @param method
     * @return
     */
    public static Request method(Class c, String method) {
        return aClass(c).filterWith(Filters.matchMethod(new Description(c, method)));
    }

    /**
     * 增加过滤器
     * 设计模式：
     *  装饰者模式：把当前request，包装上一层过滤器的request
     * @param filter
     * @return
     */
    public Request filterWith(Filter filter) {
        return new FilterRequest(this, filter);
    }
}
