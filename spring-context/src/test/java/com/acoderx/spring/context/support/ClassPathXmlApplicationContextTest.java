package com.acoderx.spring.context.support;

import com.acoderx.spring.context.entity.FooService;
import com.acoderx.spring.context.entity.TestBean;
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

    /**
     * 测试xml自定义命名空间 context:component-scan
     */
    @Test
    public void testCustomXml() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-custom.xml");
        TestBean testBean = applicationContext.getBean(TestBean.class);
        Assert.assertEquals("foo",testBean.getFoo());
    }
}