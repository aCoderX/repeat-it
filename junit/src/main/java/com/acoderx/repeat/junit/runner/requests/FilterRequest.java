package com.acoderx.repeat.junit.runner.requests;

import com.acoderx.repeat.junit.runner.manipulation.Filter;
import com.acoderx.repeat.junit.runner.Request;
import com.acoderx.repeat.junit.runner.Runner;

/**
 * Description:装饰器，给request增加过略器,比如根据方法名过滤某测试类需要执行的方法
 *
 * @author  xudi
 * @since  2018-10-29
 */
public class FilterRequest extends Request {

    private Request request;
    private Filter filter;


    public FilterRequest(Request request, Filter filter) {
        this.request = request;
        this.filter = filter;
    }

    @Override
    public Runner getRunner() {
        Runner runner = request.getRunner();
        if (filter.apply(runner)) {
            return runner;
        }
        return null;
    }
}
