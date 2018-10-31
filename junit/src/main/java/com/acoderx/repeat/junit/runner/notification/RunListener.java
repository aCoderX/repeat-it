package com.acoderx.repeat.junit.runner.notification;

/**
 * Description:订阅者
 * 设计模式：
 *  观察者模式：观察者Observer
 *
 * @author: xudi
 * @since: 2018-10-30
 */
public interface RunListener {
    //整个测试任务开始
    default void testRunStarted(){}

    //整个测试任务结束
    default void testRunFinished(){}

    //单个原子测试任务开始
    default void testStarted(){}

    //单个原子测试任务结束
    default void testFinished(){}

    //失败
    default void testFailure(Throwable throwable){}

}
