package com.acoderx.repeat.junit.runner;

import com.acoderx.repeat.junit.runner.notification.RunListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:返回结果
 *
 * @author: xudi
 * @since: 2018-10-29
 */
public class Result {
    private List<Throwable> failure = new ArrayList<>();

    /**
     * 返回一个结果的监听器
     * @return
     */
    public RunListener createListener() {
        return new Listener();
    }

    /**
     * 监听器
     * 设计模式：
     *  观察者模式
     */
    private class Listener implements RunListener {
        @Override
        public void testFailure(Throwable throwable) {
            failure.add(throwable);
        }
    }

    public List<Throwable> getFailure() {
        return failure;
    }
}
