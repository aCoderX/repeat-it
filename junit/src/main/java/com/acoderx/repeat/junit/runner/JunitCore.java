package com.acoderx.repeat.junit.runner;

/**
 * Description: 核心入口类
 *
 * @author  xudi
 * @since  2018-10-29
 */
public class JunitCore {
    public static Result run(Request request) {
        return request.getRunner().run();
    }
}
