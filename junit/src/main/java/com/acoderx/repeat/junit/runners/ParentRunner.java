package com.acoderx.repeat.junit.runners;


import com.acoderx.repeat.junit.runner.Description;
import com.acoderx.repeat.junit.runner.Result;
import com.acoderx.repeat.junit.runner.Runner;
import com.acoderx.repeat.junit.runner.manipulation.Filter;
import com.acoderx.repeat.junit.runner.manipulation.Filterable;
import com.acoderx.repeat.junit.runner.notification.RunNotifier;

import java.util.Iterator;
import java.util.List;

/**
 * Description:runner的抽象类
 *
 * @author: xudi
 * @since: 2018-10-29
 */
public abstract class ParentRunner<T> implements Runner, Filterable {
    //被过滤后的子测试项
    // Guarded by childrenLock
    private volatile List<T> filteredChildren = null;
    //锁
    private final Object childrenLock = new Object();
    //被测试类
    private Class testClass;

    /**
     * 获取所有子测试项
     * @return
     */
    abstract List<T> getChildren();

    /**
     * 运行子测试项
     * @param child
     * @param notifier
     */
    abstract void runChild(T child, RunNotifier notifier);

    /**
     * 返回子测试项的信息
     * @param child
     * @return
     */
    abstract Description describeChild(T child);

    public ParentRunner(Class testClass) {
        this.testClass = testClass;
    }

    /**
     * 运行runner，会运行相应子测试项
     * 设计模式：
     *  观察者模式：注册runListener，在相应时间触发事件执行
     *  模板方法模式：会调用getChildren()获取相应子测试项，回调runChild()运行相应子测试项
     * @return
     */
    @Override
    public Result run() {
        Result result = new Result();
        RunNotifier notifier = new RunNotifier();
        notifier.addListener(result.createListener());
        filteredChildren = getFilteredChildren();
        for (T child : filteredChildren) {
            runChild(child,notifier);
        }
        return result;
    }

    /**
     * 根据过滤器过滤相应子测试项
     * @param filter
     */
    @Override
    public void filter(Filter filter) {
        filteredChildren = getFilteredChildren();
        Iterator<T> iterator = filteredChildren.iterator();
        while (iterator.hasNext()) {
            T each = iterator.next();
            if (shouldRun(filter, each)) {
                //如果该子测试项没有被过滤，则尝试递归过滤该子测试项的子测试项
                filter.apply(each);
            } else {
                iterator.remove();
            }
        }
    }

    /**
     * 返回被测试类
     * @return
     */
    public Class getTestClass() {
        return testClass;
    }

    /**
     * 返回被测试类的信息
     * @return
     */
    @Override
    public Description getDescription() {
        return new Description(testClass);
    }

    private boolean shouldRun(Filter filter, T each) {
        return filter.shouldRun(describeChild(each));
    }

    private List<T> getFilteredChildren() {
        synchronized (childrenLock) {
            if (filteredChildren == null) {
                filteredChildren = getChildren();
            }
            return filteredChildren;
        }
    }

}
