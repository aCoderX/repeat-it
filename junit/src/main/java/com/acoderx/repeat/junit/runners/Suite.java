package com.acoderx.repeat.junit.runners;

import com.acoderx.repeat.junit.runner.Description;
import com.acoderx.repeat.junit.runner.notification.RunNotifier;
import com.acoderx.repeat.junit.runner.Runner;

import java.util.List;

/**
 * Description:多个测试类的抽象
 * 设计模式：composite模式中的父节点（Composite），包含多个子节点（如：BlockJUnit4ClassRunner）
 *
 * @author  xudi
 * @since  2018-10-29
 */
public class Suite extends ParentRunner<Runner> {
    private List<Runner> runners;

    public Suite(List<Runner> runners) {
        super(null);
        this.runners = runners;
    }

    @Override
    List<Runner> getChildren() {
        return runners;
    }

    @Override
    void runChild(Runner runner, RunNotifier notifier) {
        runner.run();
    }

    @Override
    Description describeChild(Runner child) {
        return child.getDescription();
    }
}
