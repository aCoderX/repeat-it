package com.acoderx.repeat.junit.runner.notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:当相应事件触发时，通知listener
 * 设计模式：
 *  观察者模式：被观察对象(subject)
 *
 * @author  xudi
 * @since  2018-10-30
 */
public class RunNotifier {
    List<RunListener> listeners = new ArrayList<>();

    public void addListener(RunListener listener) {
        listeners.add(listener);
    }
    public void fireTestRunStarted() {
        for (RunListener listener : listeners) {
            listener.testStarted();
        }
    }

    public void fireTestRunFinished() {
        for (RunListener listener : listeners) {
            listener.testStarted();
        }
    }

    public void fireTestStarted() {
        for (RunListener listener : listeners) {
            listener.testStarted();
        }
    }

    public void fireTestFinished() {
        for (RunListener listener : listeners) {
            listener.testStarted();
        }
    }

    public void fireTestFailure(Throwable throwable) {
        for (RunListener listener : listeners) {
            listener.testFailure(throwable);
        }
    }
}
