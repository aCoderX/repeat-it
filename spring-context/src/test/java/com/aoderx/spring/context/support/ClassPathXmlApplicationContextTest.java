package com.aoderx.spring.context.support;

import com.aoderx.spring.context.entity.FooService;
import com.aoderx.spring.context.entity.TestBean;
import org.junit.Assert;
import org.junit.Test;

public class ClassPathXmlApplicationContextTest {
    @Test
    public void test() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        TestBean testBean = (TestBean) applicationContext.getBean("testBean");
        Assert.assertEquals("2",testBean.getFoo());
    }

    @Test
    public void testPorperty() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        FooService fooService = (FooService) applicationContext.getBean("fooService");
        fooService.bar();
    }
}