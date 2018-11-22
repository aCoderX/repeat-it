package com.aoderx.spring.context.support;

import com.aoderx.spring.context.FooService;
import com.aoderx.spring.context.TestBean;
import org.junit.Test;

public class ClassPathXmlApplicationContextTest {
    @Test
    public void test() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        TestBean testBean = (TestBean) applicationContext.getBean("testBean");
        System.out.println(testBean.getFoo());
    }

    @Test
    public void testPorperty() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        FooService fooService = (FooService) applicationContext.getBean("fooService");
        fooService.bar();
    }
}