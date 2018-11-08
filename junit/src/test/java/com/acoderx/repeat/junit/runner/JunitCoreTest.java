package com.acoderx.repeat.junit.runner;

import com.acoderx.repeat.junit.UserServiceTest;

public class JunitCoreTest {

    public static void main(String[] args){
        Result result = JunitCore.run(Request.aClass(UserServiceTest.class));
        for (Throwable fail : result.getFailure()) {
            System.out.println("fail:"+fail);
        }
        if (result.getFailure().size() == 0) {
            System.out.println("All tests finished successfully...");
        }

        System.out.println("------------------");
        JunitCore.run(Request.classes(UserServiceTest.class));
        System.out.println("------------------");
        JunitCore.run(Request.method(UserServiceTest.class,"sumUserNumTimeOut"));
    }
}