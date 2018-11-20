package com.aoderx.spring.context.annotation;

import com.aoderx.spring.context.TestBean;
import org.junit.Test;

public class AnnotationConfigApplicationContextTest {
    @Test
    public void testRegister(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutowiredMethodConfig.class);
        TestBean testBean = applicationContext.getBean(TestBean.class);
        System.out.println(testBean.getFoo());
    }

    @Test
    public void testScan(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.aoderx.spring.context.annotation");
        TestBean testBean1 = applicationContext.getBean(TestBean.class);
        TestBean testBean2 = applicationContext.getBean(TestBean.class);
        System.out.println(testBean1);
        System.out.println(testBean2);
    }


    @Configuration
    static class AutowiredMethodConfig {
        @Bean
        public TestBean testBean() {
            return new TestBean();
        }
    }
}