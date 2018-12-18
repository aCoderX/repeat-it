package com.acoderx.spring.context.annotation;

import com.acoderx.spring.context.entity.FooService;
import com.acoderx.spring.context.entity.TestBean;
import org.junit.Assert;
import org.junit.Test;

public class AnnotationConfigApplicationContextTest {
    @Test
    public void testRegister(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutowiredMethodConfig.class);
        TestBean testBean = applicationContext.getBean(TestBean.class);
        Assert.assertEquals("configSet",testBean.getFoo());
    }

    /**
     * 测试@ComponentScan注解
     */
    @Test
    public void testComponentScanConfigRegister(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentScanTestConfig.class);
        TestBean testBean = applicationContext.getBean(TestBean.class);
        Assert.assertEquals("foo",testBean.getFoo());
    }

    /**
     * 测试扫描@configuration注解
     */
    @Test
    public void testScanConfigration(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.acoderx.spring.context.annotation");
        TestBean testBean = applicationContext.getBean(TestBean.class);
        FooService fooService = applicationContext.getBean(FooService.class);
        Assert.assertEquals("configSet",testBean.getFoo());
        Assert.assertEquals(testBean,fooService.getTestBean());
    }

    /**
     * 测试扫描@component
     */
    @Test
    public void testScan(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.acoderx.spring.context.entity");
        FooService fooService = applicationContext.getBean(FooService.class);
        Assert.assertEquals("foo",fooService.getTestBean().getFoo());
    }


    @Configuration
    static class AutowiredMethodConfig {
        @Bean
        public TestBean testBean() {
            TestBean t = new TestBean();
            t.setFoo("configSet");
            return t;
        }

        @Bean
        public FooService fooService() {
            return new FooService(testBean());
        }
    }

    @Configuration
    @ComponentScan
    static class ComponentScanTestConfig {
    }
}