package com.acoderx.repeat.junit.simplest;

import com.acoderx.repeat.junit.UserServiceTest;


/**
 * 最简单的实现测试，直接通过反射拿出所有方法进行运行
 */
public class JunitCoreTest {
    public static void main(String[] args) {
        runAll();
    }
    public static void runMethod() {
        Result result = new JUnitCore().run(UserServiceTest.class,"sumUser1Num");
        for (Throwable fail : result.getFailure()) {
            System.out.println("fail:"+fail);
        }
        if (result.getFailure().size() == 0) {
            System.out.println("All tests finished successfully...");
        }
    }

    public static void runAll() {
        Result result = new JUnitCore().run(UserServiceTest.class);
        for (Throwable fail : result.getFailure()) {
            System.out.println(fail.getMessage());
        }
        if (result.getFailure().size() == 0) {
            System.out.println("All tests finished successfully...");
        }
    }
}