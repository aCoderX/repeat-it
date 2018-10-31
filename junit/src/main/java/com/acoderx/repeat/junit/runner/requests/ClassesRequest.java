package com.acoderx.repeat.junit.runner.requests;

import com.acoderx.repeat.junit.runner.Request;
import com.acoderx.repeat.junit.runner.Runner;
import com.acoderx.repeat.junit.runners.Suite;
import com.acoderx.repeat.junit.runners.BlockJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:多个类的测试请求
 *
 * @author: xudi
 * @since: 2018-10-29
 */
public class ClassesRequest extends Request {
    private List<Runner> runners = new ArrayList<>();

    public ClassesRequest(Class[] classes) {
        for (Class c : classes) {
            runners.add(new BlockJUnit4ClassRunner(c));
        }
    }

    @Override
    public Runner getRunner() {
        return new Suite(runners);
    }
}
