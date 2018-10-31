package com.acoderx.repeat.junit.runner.requests;

import com.acoderx.repeat.junit.runner.Request;
import com.acoderx.repeat.junit.runner.Runner;
import com.acoderx.repeat.junit.runners.BlockJUnit4ClassRunner;

/**
 * Description:单个类的测试请求
 *
 * @author: xudi
 * @since: 2018-10-29
 */
public class ClassRequest extends Request {
    private final Class<?> fTestClass;

    public ClassRequest(Class<?> fTestClass) {
        this.fTestClass = fTestClass;
    }

    @Override
    public Runner getRunner() {
        return new BlockJUnit4ClassRunner(fTestClass);
    }
}
