package com.aoderx.spring.context.annotation;

import com.aoderx.spring.context.entity.FooService;
import com.aoderx.spring.context.entity.TestBean;
import org.junit.Test;

public class AnnotationConfigApplicationContextTest {
    @Test
    public void testRegister(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutowiredMethodConfig.class);
        TestBean testBean = applicationContext.getBean(TestBean.class);
        System.out.println(testBean.getFoo());
    }

    /**
     * 测试扫描configuration注解
     */
    @Test
    public void testScanConfigration(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.aoderx.spring.context.annotation");
        TestBean testBean = applicationContext.getBean(TestBean.class);
        FooService fooService = applicationContext.getBean(FooService.class);
        System.out.println(testBean.getFoo());
        System.out.println(testBean);
        System.out.println(fooService);
        System.out.println(fooService.getTestBean());
    }

    /**
     * 测试扫描component
     */
    @Test
    public void testScan(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.aoderx.spring.context.entity");
        FooService fooService = applicationContext.getBean(FooService.class);
        System.out.println(fooService.getTestBean().getFoo());
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
}