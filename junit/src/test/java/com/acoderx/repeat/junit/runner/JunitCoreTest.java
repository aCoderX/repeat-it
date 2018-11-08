package com.acoderx.repeat.junit.runner;

import com.acoderx.repeat.junit.UserServiceTest;

public class JunitCoreTest {

    public static void main(String[] args){
        System.out.println("--------运行单个类----------");
        Result result = JunitCore.run(Request.aClass(UserServiceTest.class));
        for (Throwable fail : result.getFailure()) {
            System.out.println("fail:"+fail);
        }
        if (result.getFailure().size() == 0) {
            System.out.println("All tests finished successfully...");
        }

        System.out.println("--------运行多个类----------");
        JunitCore.run(Request.classes(UserServiceTest.class));
        System.out.println("-------运行单个方法测试-----------");
        JunitCore.run(Request.method(UserServiceTest.class,"sumUserNum"));

        System.out.println("--------超时测试----------");
        JunitCore.run(Request.method(UserServiceTest.class,"sumUserNumTimeOut"));
    }
}